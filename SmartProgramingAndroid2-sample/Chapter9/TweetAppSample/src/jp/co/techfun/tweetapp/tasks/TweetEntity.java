package jp.co.techfun.tweetapp.tasks;

// �Ԃ₫����ێ�����N���X
public class TweetEntity {

    // ���[�U��
    private String screenName;
    // �Ԃ₫
    private String text;
    // �v���t�B�[���A�C�R���摜��URL
    private String profileImageUrlHttps;

    // �R���X�g���N�^
    public TweetEntity(String screenName, String text,
        String profileImageUrlHttps) {
        this.screenName = screenName;
        this.text = text;
        this.profileImageUrlHttps = profileImageUrlHttps;
    }

    // ���[�U����Ԃ����\�b�h�ł�
    public String getScreenName() {
        return screenName;
    }

    // �Ԃ₫��Ԃ����\�b�h�ł�
    public String getText() {
        return text;
    }

    // �v���t�B�[���A�C�R���摜��URL��Ԃ����\�b�h�ł�
    public String getProfileImageUrlHttps() {
        return profileImageUrlHttps;
    }

}
