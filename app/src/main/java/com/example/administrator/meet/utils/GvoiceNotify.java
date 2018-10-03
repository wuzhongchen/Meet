package com.example.administrator.meet.utils;

import android.widget.TextView;

import com.tencent.gcloud.voice.IGCloudVoiceNotify;

public class GvoiceNotify implements IGCloudVoiceNotify {
    private TextView mTextView;             // 显示回调结果
    private String mFileID;                 // 上传音频文件成功时服务端返回的文件唯一性标识符
    private boolean uploadSuccess = false;  // 音频文件上传成功与否的标志

    public GvoiceNotify() {
        super();
    }

    public GvoiceNotify(TextView textView) {
        mTextView = textView;
    }


    @Override
    /**
     * 加入房间时回调
     * @param completeCode:参见 IGCloudVoiceNotify.GCloudVoiceCompleteCode 定义
     * @param roomName:加入的房间名
     * @param memberID:如果加入成功的话，表示加入后的成员 ID
     */
    public void OnJoinRoom(int completeCode, String roomName, int memberID) {
        String str = "Result of OnJoinRoom: \n" +
                "Code: " + completeCode + "\n" +
                "Room name: " + roomName + "\n" +
                "Member ID: " + memberID + "\n";
        mTextView.setText(str);
    }

    @Override
    /**
     * TODO 进房断网3min后会被踢出
     * @param status
     * @param roomName:加入的房间名
     * @param memberID:如果加入成功的话，表示加入后的成员 ID
     */
    public void OnStatusUpdate(int status, String roomName, int memberID) {
        String str = "Result of OnStatusUpdate: \n" +
                "Status: " + status + "\n" +
                "Room name: " + roomName + "\n" +
                "Member ID: " + memberID + "\n";
        mTextView.setText(str);
    }

    @Override
    /**
     * 退出房间时回调
     * @param completeCode:参见 IGCloudVoiceNotify.GCloudVoiceCompleteCode 定义
     * @param roomName:加入的房间名
     */
    public void OnQuitRoom(int completeCode, String roomName) {
        String str = "Result of OnQuitRoom: \n" +
                "Code: " + completeCode + "\n" +
                "Room name: " + roomName + "\n";
        mTextView.setText(str);
    }

    @Override
    /**
     * 当房间中的其他成员开始说话或者停止说话的时候，通过该回调进行通知
     * @param members:状态发生改变的 members
     *               值为 [memberID status] 对，共有 count 对
     *               status 取值："0"：停止说话 "1"：开始说话 "2"：继续说话
     * @param count: 状态发生改变的成员数
     */
    public void OnMemberVoice(int[] members, int count) {
        String str = "Result of OnMemberVoice: \n" +
                "Count: " + count + "\n";
        for (int i = 0; i < members.length - 1; i += 2) {
            str = str + "Status of member " + i + " is: " + (i + 1) + "\n";
        }
        mTextView.setText(str);
    }

    @Override
    /**
     * 上传语音文件后的结果通过该函数进行回调
     * @param completeCode:参见 IGCloudVoiceNotify.GCloudVoiceCompleteCode 定义
     * @param filePath:上传的文件路径
     * @param fileId:文件的 ID
     *              注意！该 ID 是音频文件的唯一标识符，下载音频文件时需要提供该标识符
     */
    public void OnUploadFile(int completeCode, String filePath, String fileID) {
        String str = "Result of OnUploadFile: \n" +
                "Code: " + completeCode + "\n" +
                "file path: " + filePath + "\n" +
                "file ID: " + fileID + "\n";
        mTextView.setText(str);

        if (completeCode == GCloudVoiceCompleteCode.GV_ON_UPLOAD_RECORD_DONE) {
            uploadSuccess = true;
            mFileID = fileID;

        }
    }

    @Override
    /**
     * 下载语音文件后的结果通过该函数进行回调
     * @param completeCode:参见 IGCloudVoiceNotify.GCloudVoiceCompleteCode 定义
     * @param filePath:上传的文件路径
     * @param fileId:文件的 ID
     */
    public void OnDownloadFile(int completeCode, String filePath, String fileID) {
        String str = "Result of OnDownloadFile: \n" +
                "Code: " + completeCode + "\n" +
                "file path: " + filePath + "\n" +
                "file ID: " + fileID + "\n";
        mTextView.setText(str);
    }

    @Override
    /**
     * 如果用户没有暂停播放，而语音文件已经播放完了，通过该函数进行回调
     * @param completeCode:参见 IGCloudVoiceNotify.GCloudVoiceCompleteCode 定义
     * @param filePath:播放的文件路径
     */
    public void OnPlayRecordedFile(int completeCode, String filePath) {
        String str = "Result of OnPlayRecordedFile: \n" +
                "file path: " + filePath + "\n";
        mTextView.setText(str);
    }

    @Override
    /**
     * 请求语音消息许可的时候会回调
     * @param completeCode:参见 IGCloudVoiceNotify.GCloudVoiceCompleteCode 定义
     */
    public void OnApplyMessageKey(int completeCode) {
        String str = "Result of OnApplyMessageKey: \n"
                + "Code: " + completeCode + "\n";
        mTextView.setText(str);
    }

    @Override
    /**
     * 语音转文字的结果通过该函数回调进行通知
     * @param completeCode:参见 IGCloudVoiceNotify.GCloudVoiceCompleteCode 定义
     * @param fileID:翻译文件的 fileID
     * @param result:翻译的文字结果
     */
    public void OnSpeechToText(int completeCode, String fileID, String result) {
        String str = "Result of OnQuitRoom: \n" +
                "Code: " + completeCode + "\n" +
                "fileID: " + fileID + "\n" +
                "result: " + result + "\n";
        mTextView.setText(str);
    }

    @Override
    /**
     * TODO 内部接口，无需关心
     * @param pAudioData
     * @param nDataLength
     */
    public void OnRecording(char[] pAudioData, int nDataLength) {
        String str = "Result of OnQuitRoom: \n";
        mTextView.setText(str);
    }

    @Override
    /**
     * TODO
     * @param completeCode:参见 IGCloudVoiceNotify.GCloudVoiceCompleteCode 定义
     * @param errCode:服务器错误码
     * @param result
     */
    public void OnStreamSpeechToText(int completeCode, int errCode, String result) {
        String str = "Result of OnQuitRoom: \n" +
                "Code: " + completeCode + "\n";
        mTextView.setText(str);
    }

    /**
     * 获取上传文件成功时从服务端得到的 fileID
     * 注意！该 ID 是音频文件的唯一标识符，下载音频文件时需要提供该标识符
     *
     * @return the fileID
     */
    public String getFileID() {
        if (uploadSuccess) {
            return mFileID;
        } else {
            return "";
        }
    }
}
