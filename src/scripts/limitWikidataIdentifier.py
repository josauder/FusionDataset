import sys, os

datasetDir= "/home/jonathan/hpi/ld/datasetLarger2"
datasetTestDir= "/home/jonathan/workspace/Fusion/src/test/resources/dataset"

n=2000

def subjectUnderN(x,n):
	try:
		return int(x.split(" ")[0].replace("<http://wikidata.dbpedia.org/resource/Q","").replace(">",""))<n
	except:
		return False

for fi in os.listdir(datasetDir) :
	print fi
	if fi.endswith(".ttl") and any([fi.startswith(x) for x in ["mappingbased_objects_wkd","mappingbased_literals_wkd","instance_types_transitive_wkd"]]):
		print fi
		with open(datasetDir+"/"+fi,"r") as f:
			with open(datasetTestDir+"/"+fi,"w") as g:
				for x in f:
				    x=x.replace("http://wikidata.dbpedia.org/ontology","http://dbpedia.org/ontology").strip()
				    if subjectUnderN(x,n):
				    	print >> g, x


