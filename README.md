# DBpedia Fusion Dataset - Unifying Knowledge From i18n Chapters to Improve Information Quality and Integrity

Jonathan Sauder <jonathan.sauder@student.hpi.de>

Florian Schmidt <florian.schmidt@student.hpi.de>

## Problem And Motivation

The DBpedia community project[1] aims to make structured, multilingual knowledge available freely on the web. This knowledge stems from information extracted from all language versions of Wikipedia. Knowledge on DBpedia is stored in RDF format in multiple datasets. Thus far, there are datasets containing various subsets of DBpedia knowledge, separated by the language version of Wikipedia from which the corresponding information was extracted. The DBpedia community constantly strives to improve the quality of data in these datasets. Measures to standardize information from Wikipedia have been taken, such as the introduction of the DBpedia Ontology or the joint effort with the Wikidata project² to uniquely identify each contained entity with a URI[2]. Nonetheless, due to the localized nature of contributions to Wikipedia’s different language versions, each DBpedia language chapter contains different information. This means some entities will be more thoroughly and correctly described in certain languages, while other entities are erroneous or do not even exist in another language.  Therefore, DBpedia users only have access to an incomplete subset of the total knowledge extracted from Wikipedia. This work aims to solve this problem by fusing all DBpedia language chapters into one unified dataset. This can improve the DBpedia functionality in the following ways:

**1. Increased Integrity of Knowledge**

As mentioned above, each DBpedia language chapter contains unique information which other language chapters to not contain. A fused dataset will thus unite all knowledge into one centralized dataset which supplies a user with more complete information about more entities than contained in any single DBpedia language chapter.

**2. Redundancy and Error elimination**
Knowledge in the different DBpedia language chapters is overlapping. Therefore, different language chapters contain conflicting or redundant information. In order to create a Fusion dataset, it is necessary to identify these conflicts and automatically resolve them in a manner that will most likely include only correct information. In this process, errors and outliers can be found and eliminated, supplying the user with cleaner and more reliable information.

### Considerations for Possible Solutions

This work focusses only on information which was extracted from Wikipedia using the mappings to the DBpedia ontology and which is linked across multiple languages using Wikidata URIs as unique identifiers. We disregard data that does not match these criteria as it is not directly possible to identify conflicts in such data. RDF subjects which only exist in one language chapter can simply be included in the fusion dataset. Subjects which appear in two or more language chapters have to be merged in order to make sure that no conflicting facts are included. Due to differences in semantics and data types of properties, different merge functions are best suited for different properties. Since there are many hundred commonly used RDF properties in the DBpedia Ontology, it is necessary to separate RDF properties into a manageable amount of groups according to the best corresponding merge functions.

**1. The RDF property is suited to accept all triples**

Some properties do not create conflicts when facts from all languages are accepted. An example of this case is the rdfs:seeAlso property. Here, adding all facts will further increase the information amount without creating conflicts which could diminish correctness or quality of information.

**2. Group RDF properties by data type**

All RDF properties allow a certain range of values. A simple approach and therefore a suited default merge function could select the right facts based on possible methods for the corresponding data types. This way, the following groups can be distinguished:

*Numerical data*

An example for this kind of data is dbo:populationTotal. It is possible to use statistical methods to filter outliers and to compute an average which itself does not have to be part of the existing data.

*Metric data*

For many types of data such as certain strings or dates, a distance between two elements can be computed. Therefore it can be possible to identify outliers but we can not find a meaningful average other than the statistical mode.

*Categorical Nominal data*

When merging object properties or strings such as ISO codes, there is no meaningful distance between two or more objects. It is only possible to compute the statistical mode.

**3. Group RDF properties by semantics**

Some properties require certain semantic interpretations in order to be merged correctly. The most prevalent example is rdf:type. Conflicts in this property can not be resolved by merely comparing the amount of identical facts across multiple languages. Instead, it is necessary to review possible subclass relationships in the class tree of the DBpedia ontology, and identify conflicts in this hierarchy.

**4. Resolve Conflicts using Heuristics**

In some cases, the value of a fact might even be completely disregarded. When looking at conflicts that have already been resolved, some languages might have been included in the result more often than others. With increasing number of already solved conflicts, this heuristic could be used to favor languages with a high inclusion rate over others. A simpler heuristic could favor languages which contain a large number of facts for a subject over others with fewer facts. Heuristics like these are best suited when there are so few facts, that statistical methods are unreliable, or when multiple values are equally common.


### Scalability

To be relevant, considering the vast amount of data in the DBpedia datasets and their continuous growth, the solutions proposed in this work have to be scalable. This means the ideas for fusing the datasets will be built on existing concepts from big data applications. More specifically, the application in development uses the Apache Beam³ framework for parallelization.

### Related Work

There has been a previous effort by the DBpedia project to create a fusion dataset themselves⁴. However, it seems that this project is still in its initial phase and we can only build on the concepts mentioned.

¹downloads.dbpedia.org

²www.wikidata.org

³beam.incubator.apache.org

⁴github.com/nilesh-c/extraction-framework/blob/fuse/scripts/src/main/scala/org/dbpedia/extraction/scripts/FuseDatasets.scala


[1]    Jens Lehmann, Robert Isele, Max Jakob, Anja Jentzsch, Dimitris Kontokostas, Pablo N. Mendes, Sebastian Hellmann, Mohamed Morsey, Patrick van Kleef, Sören Auer, Christian Bizer. DBpedia – A Large-scale, Multilingual Knowledge Base Extracted from Wikipedia. Published in the Semantic Web Journal, Volume 6, Number 2, 167--195, 2015, IOS


[2]    Dimitris Kontokostas, Charalampos Bratsas, Sören Auer, Sebastian Hellmann, Ioannis Antoniou, George Metakides: Internationalization of Linked Data: The case of the Greek DBpedia edition. In Journal of Web Semantics: Science, Services and Agents on the World Wide Web, Vol.15, pp. 51-61, 2012.




# Running this Project

You will need need the following DBpedia datasets in all languages that you wish to fuse:

- `mappingbased_objects_wkd_uris_{lang}`
- `mappingbased_literals_wkd_uris_{lang}`
- `instance_types_transitive_wkd_uris_{lang}`

Alternatively, a test-dataset is provided in `src/test/resources/dataset`, which contains subsets of the dataset with RDF Subjects with Wikidata-IDs under 2000.

Set the parameters in `application.properties` correctly:

- `datasetDirectory` : path to folder containing dataset mentioned above
- `targetDirectory` : path to directory that will contain output
- `ontologyFile` : path to current Version of DBpedia Ontology (as n-triple format)

Set the logging output correctly in `log4j.properties` -> `log4j.appender.file={path_to_log_file}`

Run maven with right profile (`-P direct-runner` or `-P spark-runner`)

Run FusionDataset.main with correct runner in arguments (`--runner=SparkRunner` for Apache Spark, `--runner=DirectRunner` for Local Java) or run FusionDatasetTest

Note that this Project is being developed as a college project and therefore might have subpar structure or documentation.

Feel free to e-mail us if questions arise.
