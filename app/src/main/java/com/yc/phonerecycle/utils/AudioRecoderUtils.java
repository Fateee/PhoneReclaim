package com.yc.phonerecycle.utils;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class AudioRecoderUtils {
    private static ExecutorService mExecutorService = newSingleThreadExecutor();
    private boolean isPlaying;//播放状态
    private MediaPlayer mMediaPlayer;//播放控件
    public String filePath;
    public String fileName = "recorder.amr";;
    private MediaRecorder mMediaRecorder;
    private final String TAG = "MediaRecord";
    public static final int MAX_LENGTH = 1000 * 60 * 10;// 最大录音时长1000*60*10;

    public static boolean isStart = false;
    private String IMAGE_DIR = "phonerecycle/image";
    private OnAudioStatusUpdateListener audioStatusUpdateListener;
    private String mUploadfilePath;

    public AudioRecoderUtils() {
        if (DeviceUtil.checkSDCardAvailable()) {
            mUploadfilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + IMAGE_DIR;
            FileUtils.makeDirs(mUploadfilePath, true);
        }
        //开始录音创建新文件路径
        this.filePath = mUploadfilePath + "/" + fileName;
    }

    private long startTime;
    private long endTime;

    /**
     * 开始录音 使用amr格式
     * 录音文件
     *
     * @return
     */
    public boolean startRecord() {
        boolean ret = false;
        try {
            if (!Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {//存储媒体已经挂载，并且挂载点可读/写。
                Log.e(TAG, "SD is error!");
                return ret;
            }
//            if (DeviceUtil.checkSDCardAvailable()) {
//                mUploadfilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + IMAGE_DIR;
//                FileUtils.makeDirs(mUploadfilePath, true);
//            }
//            //开始录音创建新文件路径
//            this.filePath = mUploadfilePath + "/" + fileName;
            File file = new File(this.filePath);
            //文件若存在删除，防止录音覆盖问题
            if (!file.exists()) {
                file.createNewFile();//创建新文件
            }

            // 开始录音
            /* ①Initial：实例化MediaRecorder对象 */
            if (mMediaRecorder == null) {
                mMediaRecorder = new MediaRecorder();
            } else {
                mMediaRecorder.stop();
                mMediaRecorder.reset();
                mMediaRecorder.release();
                mMediaRecorder = new MediaRecorder();
            }

            /* ②setAudioSource/setVedioSource */
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样 */
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            /*
             * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
             * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
             */
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            /* ③准备 */
            mMediaRecorder.setOutputFile(filePath);
            mMediaRecorder.setMaxDuration(MAX_LENGTH);

            mMediaRecorder.prepare();
            /* ④开始 */
            mMediaRecorder.start();
            isStart = true;
            // AudioRecord audioRecord.
            /* 获取开始时间* */
            startTime = System.currentTimeMillis();
            updateMicStatus();
            Log.i("ACTION_START", "startTime" + startTime);
            ret = true;
        } catch (Exception e) {
            Log.i(TAG, "call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        }
        return ret;
    }

    /**
     * 停止(取消)录音
     */
    public long stopRecord() {
        if (AudioRecoderUtils.isStart) {
            AudioRecoderUtils.isStart = false;
            if (mMediaRecorder == null)
                return 0L;
            endTime = System.currentTimeMillis();
            Log.i("ACTION_END", "endTime" + endTime);
            //有一些网友反应在5.0以上在调用stop的时候会报错，翻阅了一下谷歌文档发现上面确实写的有可能会报错的情况，捕获异常清理一下就行了，！
            try {
                mMediaRecorder.stop();
                mMediaRecorder.reset();
                mMediaRecorder.release();
                mMediaRecorder = null;

            } catch (RuntimeException e) {
                mMediaRecorder.reset();
                mMediaRecorder.release();
                mMediaRecorder = null;
                e.printStackTrace();
            }
            return endTime - startTime;
        }
        return 0L;
    }

    private final Handler mHandler = new Handler();

    private Runnable mUpdateMicStatusTimer = new Runnable() {
        public void run() {
            updateMicStatus();
        }
    };

    /**
     * 更新话筒状态
     */
    private int BASE = 1;
    private int SPACE = 100;// 间隔取样时间

    public void setOnAudioStatusUpdateListener(OnAudioStatusUpdateListener audioStatusUpdateListener) {
        this.audioStatusUpdateListener = audioStatusUpdateListener;
    }

    private void updateMicStatus() {
        if (mMediaRecorder != null) {
            double ratio = (double) mMediaRecorder.getMaxAmplitude() / BASE;
            double db = 0;// 分贝
            if (ratio > 1) {
                db = 20 * Math.log10(ratio);
                if (null != audioStatusUpdateListener) {
                    audioStatusUpdateListener.onUpdate(db);
                }
            }
            mHandler.postDelayed(mUpdateMicStatusTimer, SPACE);
        }
    }

    public interface OnAudioStatusUpdateListener {
        public void onUpdate(double db);
    }

    //播放音乐
    public boolean playAudio() {
        if (null != filePath && !isPlaying) {
            isPlaying = true;
            mExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    startPlay();
                }
            });
            return true;
        }
        return false;
    }

    public boolean startPlay() {
        boolean ret = false;
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        try {
            //初始化播放器
            mMediaPlayer = new MediaPlayer();
            //设置播放音频数据文件
            mMediaPlayer.setDataSource(filePath);
            //设置播放监听事件
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    //播放完成
                    playEndOrFail(true);
                }
            });
            //播放发生错误监听事件
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    playEndOrFail(false);
                    return true;
                }
            });
            //播放器音量配置
            mMediaPlayer.setVolume(1, 1);
            //是否循环播放
            mMediaPlayer.setLooping(false);
            //准备及播放
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            ret = true;
        } catch (IOException e) {
            e.printStackTrace();
            //播放失败正理
            playEndOrFail(false);
        }
        return ret;
    }

    /**
     * @description 停止播放或播放失败处理
     * @author ldm
     * @time 2017/2/9 16:58
     */
    public void playEndOrFail(boolean isEnd) {
        isPlaying = false;
        //TODO isEnd  用于播放状态提示（使用Handler）
        if (null != mMediaPlayer) {
            mMediaPlayer.setOnCompletionListener(null);
            mMediaPlayer.setOnErrorListener(null);
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
