import numpy as np
import matplotlib.pyplot as plt
import os, sys
logpath="/home/jonathan/Desktop/log.out"
#logpath="/home/jonathan/workspace/Fusion/outputs/log.out"


FloatModeResolver={}
ModeResolver={}

a=[]
b=[]
c=[]
d=[]

with open(logpath,"r") as f:
	for line in f:
		if line.find("DEBUG")>=0:
			if line.find("Initialized with:")>=0:
				line=line+" "
				a.append(int(line.split("n_facts=")[1].split(" ")[0]))
				b.append(int(line.split("n_languages=")[1].split(" ")[0]))
				c.append(int(line.split("n_mostCommonLanguage=")[1].split(" ")[0]))
				d.append(int(line.split("n_smallLanguages=")[1].split(" ")[0]))
				
			if line.find("de.kdld16.hpi.modes.Mode")>=0:
				percentage = float(line.split("Resolved with ")[1].split("%")[0])
				outOf= float(line.split(") for property")[0].split("/")[1])

				prop= line.split("property: ")[1].strip()
				if ModeResolver.has_key(prop):
					ModeResolver[prop].append((percentage,outOf))
				else:
					ModeResolver[prop]=[(percentage,outOf)]
			if line.find("de.kdld16.hpi.modes.NumericMode")>=0:
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
#print "TOTAL:"
bar=[]
perc=[]
for percentage in range(0,100,10):
	arr = len([x for x in TotalModeResolver[:,0] if x<=percentage+10 and x>percentage])
	bar.append(arr)
	perc.append(float(arr)*100./total)
#	print percentage, "\t", percentage-10, "\t",arr, "\t", "/" , "\t", total, "\t", float(arr)/total

fig,ax=plt.subplots()
ax.bar(range(0,100,10),perc,10)
for k,i in enumerate(range(0,100,10)):
	ax.annotate(bar[k],(i,perc[k]+5))
#	ax.annotate(i,(i,0))
plt.xticks(range(0,100,10),range(0,100,10))
ax.set_xlim((0,100))
ax.set_ylim((0,100))

print "COUNT:"
"""
plt.tick_params(
    axis='x',          # changes apply to the x-axis
    which='both',      # both major and minor ticks are affected
    bottom='off',      # ticks along the bottom edge are off
    top='off',         # ticks along the top edge are off
    labelbottom='off')
"""

def plot(axarr,ModeData,n=1,offset=0,co="blue"):
	for i in range(2,9):
		arr = np.array([x for x in ModeData if x[1]==i])
		arrLen= len(arr)
		print i,":",arrLen,arrLen/float(total)
		if arrLen>0:
			bar=[]
			perc=[]
			move=int(-(100./i))
			for kk in range(i):
				percentage=100-(kk*100./i)
				percentage2=100-((kk+1)*100./i)
				arr2 = len([x for x in arr[:,0] if x<=percentage+0.01 and x>percentage2+0.01])
			#	print (kk,percentage+0.01, percentage2+0.01)
				bar.append(arr2)
				perc.append(float(arr2)*100./arrLen)
#				print "\t", "\t",percentage, "\t", percentage-10, "\t",arr2, "\t", "/" , "\t", arrLen, "\t", float(arr2)/arrLen
			
			
			ii=0
			if i>=9:
				ii=1
#			fig=axarr[ii,(i-2)%7]	
			fig=axarr[(i-2)%7]
			fig.bar(np.arange(i)+offset,perc,1./n,color=co)
			fig.label=str(i)
			#fig.axes.get_xaxis().set_visible(False)
			fig.set_ylim((0,100))
			fig.set_title(i)
			
			fig.grid()
			xticks=[]
			for k in range(i):
				j=100-(k*100./i)	
				fig.annotate(str(bar[k]),(k+offset,perc[k]))
				xticks.append(str(int(round(j))))
			plt.sca(axarr[(i-2)%7])
			plt.xticks(range(i),xticks)
#plot(TotalModeResolver,"Total Mode Data")

allFloats=[]
for v in FloatModeResolver.itervalues():
	allFloats.extend(v)
allModes=[]
for v in ModeResolver.itervalues():
	allModes.extend(v)



figgg,axarr=plt.subplots(3,7)    

plot(axarr[0],TotalModeResolver,1)
plot(axarr[1],allModes,1,0,"navy")
plot(axarr[2],allFloats,1,0,"grey")


figgg,axarr=plt.subplots(3,7)    
plot(axarr[0],TotalModeResolver,1)
plot(axarr[1],ModeResolver["<http://dbpedia.org/ontology/birthDate>"],1,0,"navy")
plot(axarr[2],FloatModeResolver["<http://dbpedia.org/ontology/populationTotal>"],1,0,"grey")




objectKeys=["<http://dbpedia.org/ontology/capital>","<http://dbpedia.org/ontology/officialLanguage>","<http://dbpedia.org/ontology/currency>", "<http://dbpedia.org/ontology/largestCity>","<http://dbpedia.org/ontology/country>","<http://dbpedia.org/ontology/timeZone>","<http://dbpedia.org/ontology/language>","<http://dbpedia.org/ontology/birthPlace>","<http://dbpedia.org/ontology/deathPlace>"]
objectModes=[]




for v in objectKeys:
	if ModeResolver.has_key(v):
		objectModes.extend(ModeResolver[v])

nonobjectModes=[]
for v in ModeResolver.keys():
	if v not in objectKeys:
		nonobjectModes.extend(ModeResolver[v])


figgg,axarr=plt.subplots(3,7)    
plot(axarr[0],TotalModeResolver,1)
plot(axarr[1],objectModes,1,0,"navy")
plot(axarr[2],nonobjectModes,1,0,"grey")




fig=plt.figure()
n, bins, patches = plt.hist(a,50,normed=1,facecolor="blue")

fig=plt.figure()
b=np.array(b)

bars=np.array([len([x for x in b if x==i]) for i in range(28)])

print len([x for x in d if x>0])/float(len(d))

plt.bar(range(28),bars/float(len(b)))	
plt.show()







