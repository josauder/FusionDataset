import numpy as np
import matplotlib.pyplot as plt
import os, sys

logpath="/home/jonathan/hpi/ld/fusion/Fusion/outputs/log.out"


ModeResolver=[]

with open(logpath,"r") as f:
	for line in f:
		if line.find("DEBUG")>=0:
			if line.find("de.kdld16.hpi.resolver.ModeResolver")>=0:
				percentage = float(line.split("Resolved with ")[1].split("%")[0])
				outOf= float(line.split(") for property")[0].split("/")[1])
				ModeResolver.append((percentage,outOf))


ModeResolver=np.array(ModeResolver)

total = len(ModeResolver)

print "TOTAL:"
for percentage in range(100,20,-10):
	arr = len([x for x in ModeResolver[:,0] if x<=percentage and x>percentage-10])
	print percentage, "\t", percentage-10, "\t",arr, "\t", "/" , "\t", total, "\t", float(arr)/total

print "COUNT:"
for i in range(2,9):
	arr = np.array([x for x in ModeResolver if x[1]==i])
	arrLen= len(arr)
	print i,":",arrLen,arrLen/float(total)
	
	
	for percentage in range(100,20,-10):
		
		arr2 = len([x for x in arr[:,0] if x<=percentage and x>percentage-10])
		print "\t", "\t",percentage, "\t", percentage-10, "\t",arr2, "\t", "/" , "\t", total, "\t", float(arr2)/arrLen
