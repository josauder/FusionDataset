
import os,sys

path = "/home/jonathan/workspace/gh-copy-of-Fusion/Fusion/src/test/resources/dataset"
outputPath = "/home/jonathan/workspace/gh-copy-of-Fusion/Fusion/src/test/resources/functional_properties"

def getIDandProperty(line):
	line=line.split(" ")
	return line[0],line[1]
	
	
functional_properties=set()

non_functional_properties=set()


for fi in os.listdir(path):
	if fi.endswith(".ttl") and (fi.find("mappingbased_objects")>=0 or fi.find("mappingbased_literals")>=0):		
		
		
		with open(path+"/"+fi) as f:
			
			current_ID = None
			current_props = set()
			
			for line in f:
				ID, prop = getIDandProperty(line)
				if prop not in non_functional_properties:
					if ID == current_ID:
						if prop in current_props:
							functional_properties.discard(prop)
							non_functional_properties.add(prop)
						else:
							current_props.add(prop)
							functional_properties.add(prop)
					else:
						current_props=set()
						current_ID = ID
						current_props.add(prop)
						functional_properties.add(prop)
		print fi , len(functional_properties), len(non_functional_properties)
	
with open(outputPath, 'w') as f:	
	for prop in functional_properties:
		print >> f, prop
