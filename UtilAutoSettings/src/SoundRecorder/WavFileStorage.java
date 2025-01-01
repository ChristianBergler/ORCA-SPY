package SoundRecorder;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat.Encoding;

import PamUtils.FileFunctions;
import PamUtils.PamCalendar;
import simulatedAcquisition.UtilAutoSetting;
import wavFiles.ByteConverter;
import wavFiles.WavFile;
import wavFiles.WavFileWriter;

/**
 * Bespoke system for storing wav files. Am too sick of the complicated piped system 
 * required to use the standard Java libraries. 
 * @author dg50
 *
 */
public class WavFileStorage implements RecorderStorage {

	private RecorderControl recorderControl;
	private Type audioFileType;
	private AudioFormat audioFormat;
	private long fileStartMillis;
	private long lastDataTime;
	private WavFileWriter wavFile;

	public WavFileStorage(RecorderControl recorderControl) {
		super();
		this.recorderControl = recorderControl;
	}

	@Override
	public boolean openStorage(Type fileType, long recordingStart, float sampleRate, int nChannels, int bitDepth) {

		closeStorage();

		//		this.sampleRate = sampleRate;
		this.audioFileType = fileType;
		//		this.nChannels = nChannels;
		//		this.bitDepth = bitDepth;
		//		this.fileType = fileType;

		boolean isBigendian = (fileType != audioFileType.WAVE);

		audioFormat = new AudioFormat(sampleRate, bitDepth, nChannels, true, isBigendian);

		return openStorage(recordingStart);
	}


	private boolean openStorage(long recordingStart) {

		//		byteConverter = ByteConverter.createByteConverter(bitDepth/8, isBigendian, Encoding.PCM_SIGNED);

		//		totalFrames = 0;

		lastDataTime = fileStartMillis = recordingStart;

		//		fileBytes = 0;
		String fileExtension = "." + audioFileType.getExtension();
		File outFolder = FileFunctions.getStorageFileFolder(recorderControl.recorderSettings.outputFolder,
				recordingStart, recorderControl.recorderSettings.datedSubFolders, true);
		if (outFolder == null) {
			outFolder = new File(recorderControl.recorderSettings.outputFolder);
		}
		String fileName;
		if(UtilAutoSetting.isActive)
		{
			fileName = outFolder.getAbsolutePath()+"\\"+UtilAutoSetting.getWaveName()+".wav";
			if(fileName.equals(outFolder.getAbsolutePath()))
			{
				fileName = PamCalendar.createFileNameMillis(recordingStart, outFolder.getAbsolutePath(), 
						recorderControl.recorderSettings.fileInitials+"_", fileExtension);
			}
		}
		else
		{
			fileName = PamCalendar.createFileNameMillis(recordingStart, outFolder.getAbsolutePath(), 
					recorderControl.recorderSettings.fileInitials+"_", fileExtension);
//			System.out.println(fileName);
		}
		//	System.out.println(fileName);

		wavFile = new WavFileWriter(fileName, audioFormat);

		return true;
	}

	@Override
	public boolean reOpenStorage(long recordingStart) {	
		//		return openStorage(fileType, recordingStart, sampleRate, nChannels, bitDepth);
		closeStorage();
		return openStorage(recordingStart);
	}

	@Override
	public boolean addData(long dataTimeMillis, double[][] newData) {
		if (wavFile == null||newData == null) {
			return false;
		}
		lastDataTime = dataTimeMillis;
		return wavFile.write(newData);
	}

	@Override
	public boolean closeStorage() {
		if (wavFile == null) {
			return false;
		}
		wavFile.close();
		wavFile = null;
		return true;
	}

	@Override
	public String getFileName() {
		if (wavFile == null) {
			return null;
		}
		return wavFile.getFileName();
	}

	@Override
	public long getFileSizeBytes() {
		if (wavFile == null) {
			return 0;
		}
		return wavFile.getWavHeader().getDataSize() + wavFile.getWavHeader().getHeaderSize();
	}

	@Override
	public long getFileFrames() {
		if (wavFile == null) {
			return 0;
		}
		return wavFile.getFileFrames();
	}

	@Override
	public long getFileMilliSeconds() {
		return lastDataTime - fileStartMillis;
	}

	@Override
	public long getFileStartTime() {
		return fileStartMillis;
	}

	@Override
	public long getMaxFileSizeBytes() {
		return Integer.MAX_VALUE;
	}

}
