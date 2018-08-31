package mp3.info;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.*;

public class ClassifyBySinger {

    public static void main(String[] args){
        ClassifyBySinger classifyBySinger = new ClassifyBySinger();
        File root = new File("F:\\mp3test");
        try {
            classifyBySinger.ClassifyFiles(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ClassifyFiles(File dir) throws Exception{
        File[] files = dir.listFiles();
        for(int i =  0;i<files.length;i++){
            try {
                //get song's singer
                AudioFile music = AudioFileIO.read(files[i]);
                Tag tag = music.getTag();
                String singer = tag.getFirst(FieldKey.ARTIST);
                //get song's origin path
                String rootPath = files[i].getParent();
                //get song's old path
                String oldPath = files[i].getPath();
                //make the new path
                String newPath = rootPath + File.separator + singer;
                //new path + name
                String newPlace = newPath + File.separator + files[i].getName();
                NewFolder(newPath);
                MoveFile(oldPath,newPlace);
            }catch (CannotReadException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TagException e) {
                e.printStackTrace();
            } catch (ReadOnlyFileException e) {
                e.printStackTrace();
            } catch (InvalidAudioFrameException e) {
                e.printStackTrace();
            }
        }
    }

    //private void GetInfo(File music){
    //    try {
    //        AudioFile audioFile = AudioFileIO.read(music);
    //        Tag tag = audioFile.getTag();
    //        String singer = tag.getFirst(FieldKey.ARTIST);
    //    } catch (CannotReadException e) {
    //        e.printStackTrace();
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //    } catch (TagException e) {
    //        e.printStackTrace();
    //    } catch (ReadOnlyFileException e) {
    //        e.printStackTrace();
    //    } catch (InvalidAudioFrameException e) {
    //        e.printStackTrace();
    //    }
    //
    //}

    private void NewFolder(String folderPath){
        try {
            //String filePath = folderPath;
            //filePath = filePath.toString();
            //File newFilePath = new File(filePath);
            File newFilePath = new File(folderPath);
            if (!newFilePath.exists()) {
                newFilePath.mkdirs();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void CopyFile(String oldPath,String newPath){
        try{
            int byteread = 0;
            File oldFile = new File(oldPath);
            if(oldFile.exists()){
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while((byteread = inStream.read(buffer)) != -1){
                    fs.write(buffer,0,byteread);
                }
                inStream.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void DeleteFile(String filePath){
        try {
            //String delFilePath = filePath;
            //delFilePath = delFilePath.toString();
            //File delPath = new File(delFilePath);
            File delPath = new File(filePath);
            delPath.delete();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void MoveFile(String oldPath,String newPath){
        CopyFile(oldPath,newPath);
        DeleteFile(oldPath);
    }

}
