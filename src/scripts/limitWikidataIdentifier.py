import sys, os

datasetDir= "/home/jonathan/hpi/ld/datasets"
datasetTestDir= "/home/jonathan/hpi/ld/datasetsTest"

n=2000

def subjectUnderN(x,n):
	try:
		return int(x.split(" ")[0].replace("<http://wikidata.dbpedia.org/resource/Q","").replace(">",""))<n
	except:
		return False

for fi in os.listdir(datasetDir) :
	if any([fi.startswith(x) for x in ["mappingbased_objects_wkd","mappingbased_literals_wkd","instance_types_transitive_wkd"]]):
		print fi
		with open(datasetDir+"/"+fi,"r") as f:
			with open(datasetTestDir+"/"+fi,"w") as g:
				for x in f:
					if subjectUnderN(x,n):
						print >> g, x
