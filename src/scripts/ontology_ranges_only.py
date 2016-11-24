import sys, os

ontologyDir= "/home/jonathan/hpi/ld/fusion/Fusion/src/test/resources/ontology"

for fi in os.listdir(ontologyDir):
	if not fi.find("ranges_only")>0:
	    with open(ontologyDir+"/"+fi,"r") as f:
		    with open(ontologyDir+"/"+fi.replace(".","_ranges_only."),"w") as g:
			    for x in f:
			        if x.find("<http://www.w3.org/2000/01/rdf-schema#range>")>0:
			    	    print >> g, x.strip()


