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

fig,axarr=plt.subplots(2,6)

for i in range(2,12):
	

	
	arr = np.array([x for x in TotalModeResolver if x[1]==i])
	arrLen= len(arr)
	print i,":",arrLen,arrLen/float(total)
	
	bar=[]
	perc=[]
	move=int(-(100./i))
	for percentage in range(100,20,move):
		
		arr2 = len([x for x in arr[:,0] if x<=percentage and x>percentage-10])
		bar.append(arr2)
		perc.append(float(arr2)/arrLen)
		print "\t", "\t",percentage, "\t", percentage-10, "\t",arr2, "\t", "/" , "\t", arrLen, "\t", float(arr2)/arrLen
	
	ii=0
	if i>=6:
		ii=1
	fig=axarr[ii,i%6]	
	fig.bar(range(90,10,move),bar,-move)
	fig.label=str(i)
	fig.axes.get_xaxis().set_visible(False)
	for k,j in enumerate(range(90,10,move)):
		fig.annotate(str(round(perc[k]*100,2)),(j,bar[k]))
		progress=int(round(((100./i)*(k+1))))
		fig.annotate(str(progress),(progress,0))
		
plt.show()

	



