import numpy as np
import matplotlib.pyplot as plt
import os, sys

logpath="/home/jonathan/hpi/ld/fusion/Fusion/outputs/log.out"


FloatModeResolver={}
ModeResolver={}

with open(logpath,"r") as f:
	for line in f:
		if line.find("DEBUG")>=0:
			print line
			if line.find("de.kdld16.hpi.resolver.ModeResolver")>=0:
				percentage = float(line.split("Resolved with ")[1].split("%")[0])
				outOf= float(line.split(") for property")[0].split("/")[1])
				prop= line.split("property: ")[1].strip()
				if ModeResolver.has_key(prop):
					ModeResolver[prop].append((percentage,outOf))
				else:
					ModeResolver[prop]=[(percentage,outOf)]
			if line.find("de.kdld16.hpi.resolver.FloatModeResolver")>=0:
				percentage = float(line.split("Resolved with ")[1].split("%")[0])
				outOf= float(line.split(") for property")[0].split("/")[1])
				prop= line.split("property: ")[1].strip()
				if FloatModeResolver.has_key(prop):
					FloatModeResolver[prop].append((percentage,outOf))
				else:
					FloatModeResolver[prop]=[(percentage,outOf)]

l=[]
for v in FloatModeResolver.itervalues():
	l.extend(v)
for v in ModeResolver.itervalues():
	l.extend(v)

TotalModeResolver=np.array(l)

total = len(TotalModeResolver)

print "TOTAL:"
bar=[]
perc=[]
for percentage in range(100,20,-10):
	arr = len([x for x in TotalModeResolver[:,0] if x<=percentage and x>percentage-10])
	bar.append(arr)
	perc.append(float(arr)/total)
	print percentage, "\t", percentage-10, "\t",arr, "\t", "/" , "\t", total, "\t", float(arr)/total

fig=plt.figure()
plt.bar(range(90,10,-10),bar,10)
for k,i in enumerate(range(90,10,-10)):
	plt.annotate(round(perc[k]*100,2),(i+5,0))

print "COUNT:"



plt.tick_params(
    axis='x',          # changes apply to the x-axis
    which='both',      # both major and minor ticks are affected
    bottom='off',      # ticks along the bottom edge are off
    top='off',         # ticks along the top edge are off
    labelbottom='off')
    
def plot(ModeData,name):

	fig,axarr=plt.subplots(2,5)
	fig.suptitle(name)
	
	for i in range(2,12):
		
		arr = np.array([x for x in ModeData if x[1]==i])
		arrLen= len(arr)
		print i,":",arrLen,arrLen/float(total)
		if arrLen>0:
			bar=[]
			perc=[]
			move=int(-(100./i))
			for kk in range(i):
				percentage=100-(kk*100./i)
				arr2 = len([x for x in arr[:,0] if x<=percentage and x>percentage-((kk+1)*100./i)])
				bar.append(arr2)
				perc.append(float(arr2)/arrLen)
				print "\t", "\t",percentage, "\t", percentage-10, "\t",arr2, "\t", "/" , "\t", arrLen, "\t", float(arr2)/arrLen
			
			
			ii=0
			if i>=7:
				ii=1
			fig=axarr[ii,(i-2)%5]	
			fig.bar(range(i),bar,1)
			fig.label=str(i)
			fig.axes.get_xaxis().set_visible(False)
			
			fig.set_title(i)
			for k in range(i):
				j=100-(k*100./i)	
				fig.annotate(str(round(perc[k]*100,2)),(j,bar[k]))
				
				fig.annotate(str(int(round(j))),(k,0))

plot(TotalModeResolver,"Total Mode Data")

plot(FloatModeResolver["<http://dbpedia.org/ontology/populationTotal>"],"dbo:populationTotal")

plot(ModeResolver["<http://dbpedia.org/ontology/birthDate>"],"dbo:birthDate")
allFloats=[]
for v in FloatModeResolver.itervalues():
	allFloats.extend(v)
allModes=[]
for v in ModeResolver.itervalues():
	allModes.extend(v)
plot(allFloats,"FloatModeResolver")
plot(allModes,"ModeResolver")

print ModeResolver.keys()

objectModes=[]
for v in ["<http://dbpedia.org/ontology/capital>","<http://dbpedia.org/ontology/officialLanguage>","<http://dbpedia.org/ontology/currency>", "<http://dbpedia.org/ontology/largestCity>","<http://dbpedia.org/ontology/country>","<http://dbpedia.org/ontology/timeZone>","<http://dbpedia.org/ontology/language>","<http://dbpedia.org/ontology/birthPlace>","<http://dbpedia.org/ontology/deathPlace>"]:
	objectModes.extend(ModeResolver[v])
plot(objectModes,"ObjectModes")
plt.show()




