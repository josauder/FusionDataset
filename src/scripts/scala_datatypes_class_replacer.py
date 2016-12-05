import sys, os

srcdir = "/home/jonathan/hpi/ld/fusion/Fusion/src/test/resources/ScalaOntology"
name="OntologyTypes.scala"


toStandardUnit={}

with open (srcdir+"/"+name,"r") as f:
	with open(srcdir+"/output","w") as g:
		standardUnitDatatype=None
		
		for line in f:
			if line.find(".addDimension")>=0:

				print >> g, "\nNEW DIMENSION\n"
			if line.find(".addDimension")<0 and line.find("addLiteral")<0:
				
				li=line.split("\"")
				if len(li)>1:
					li = li[1]
					li=li.replace("xsd:","<http://www.w3.org/2001/XMLSchema#")
					li=li.replace("rdf:","<http://www.w3.org/1999/02/22-rdf-syntax-ns#")
					if not li.startswith("<"):
						li="<http://dbpedia.org/datatype/"+li
					li="\""+li+">\","
					print >> g, line

			if line.find("StandardUnitDatatype(")>=0 and line.find("//")<0:
				li=line.split("new StandardUnitDatatype(\"")[1]
				standardUnitDatatype=li.split("\"")[0].strip()
				toStandardUnit[standardUnitDatatype]={standardUnitDatatype:1}
				
				print standardUnitDatatype ,"standard!"
			if line.find("new FactorUnitDatatype(\"")>=0 and line.find("//")<0:
				li=line.split("new FactorUnitDatatype(\"")[1]
				datatype=li.split("\"")[0]
				value=li.split("),")[1].split("));")[0].strip()
				if value.find("/")>=0:
					print value
					l=value.split("/")
					value=float(l[0].strip())/float(l[1].strip())
				else:
					value=float(value)
				print datatype
				print value
				toStandardUnit[datatype]={standardUnitDatatype:value}
				toStandardUnit[standardUnitDatatype][datatype]=1/value


