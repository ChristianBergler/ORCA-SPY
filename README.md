    Code Extension for Pamguard    
    Copyright (C) 2020 Christopher Hauer

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

Installation:
The code extensions are compatible with uncompiled Pamguard Java Version
(PAMGUARD Version 2.01.04 branch BETA). Versions are available on Pamguards 
official SourceForge Website(https://sourceforge.net/projects/pamguard/).
To run the entire testing suite you'll require a compatible Python version 
such as 3.8.7 as well.2. as pytorch 1.2.0 and torchvision 0.4.0. Follow the 
Orcaspot installation at 
(https://github.com/ChristianBergler/ORCA-SPOT). 
For Pamguard you will need Javacompatible IDE such as Eclipse, an 13.0.2 SDK 
with JavaFX and Java3D. Follow the Pamguard for Developer Guide on 
(http://www.pamguard.org/).

Copy and overwrite the src folder from the code extension on the Pamguard 
Project src folder.

How to use:
enable SMRU by adding -smru to the program runtime arguments.
enable rawDeepLearningClassifier inside Pammodel.

	SimWaveInserter
The SimWaveInserter Code Extension allows one to add a wave file as a source inside Pamguards
Simulates Sound Acquisition. We developed this code extension to add orca calls as sources.
Adjust sourceWave path inside the SimWaveInserter.java accordingly, the CSVLog is optional. 
If done correctly the SimWaveInserter should be selectable inside the Simulated Sound 
Acquisition. 

	UtilAutoSetting(Optional)
The UtilAutoSetting class can restart Pamguards simulation automatically and adjust the 
simulation parameters. It requiers a csv file with the same structure as the example.csv,
the struktur of the File is: 
Callname.wav, Distanz in m, Depth in m, Arraystructure(long short[overwritten]), Interference strength in db
  
Adjust path2wave, path2csv, and optionally path2log inside UtilAutoSetting accordingly. 
If done correctly the Pamguard Simulation should restart for every line inside example.csv.

	CSVLog(Optional)
A Logger for the bearing calculator results. 
Adjust TextLogFile, CSVFile inside CSVLog accordingly, if done correctly Pamguard will create 
a log file for bearing calculations.

This unofficial code extension is not supported by Pamguard, the purpose of this code extension
is for reenacting the experiments from “ORCA-SPY: Killer Whale Localization in PAMGuard 
Utilizing Integrated Deep Learning Based Segmentation”. 
DOI:
Technical support:
On questions regarding the implementation or usage of the code extensions, please contact us 
through via email at:
Hauerch71498@th-nuernberg.de (alternative: Hauechri@gmail.com)
christian.bergler@fau.de

