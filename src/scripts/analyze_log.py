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

