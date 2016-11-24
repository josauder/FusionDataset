import sys, os

srcdir = "/home/jonathan/hpi/ld/fusion/Fusion/src/test/resources/ScalaOntology"
name="OntologyTypes.scala"

with open (srcdir+"/"+name,"r") as f:
	with open(srcdir+"/output","w") as g:
		
		for line in f:
			if line.find(".addDimension")>=0:

				print >> g, "\nNEW DIMENSION\n"
			if line.find(".addDimension")<0 and line.find("addLiteral")<0:
				
				line=line.split("\"")
				if len(line)>1:
					line = line[1]
					line=line.replace("xsd:","<http://www.w3.org/2001/XMLSchema#")
					line=line.replace("rdf:","<http://www.w3.org/1999/02/22-rdf-syntax-ns#")
					if not line.startswith("<"):
						line="<http://dbpedia.org/datatype/"+line
					line="\""+line+">\","
					print >> g, line
