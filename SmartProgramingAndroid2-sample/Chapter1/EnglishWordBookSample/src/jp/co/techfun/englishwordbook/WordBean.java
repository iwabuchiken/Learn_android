package jp.co.techfun.englishwordbook;

// �p�P��E���{���JavaBeans
public class WordBean {
	// �p�P��
	private String englishword;
	// ���{���
	private String japaneseword;
	
	// �R���X�g���N�^
    public WordBean(String englishword, String japaneseword) {

        // �p�P���ݒ�
        this.englishword = englishword;

        // ���{����ݒ�
        this.japaneseword = japaneseword;
    }
	
	// �p�P���Ԃ����\�b�h
    public String getEnglishword() {
        return englishword;
    }
    
    // �p�P���ݒ肷�郁�\�b�h
    public void setEnglishword(String englishword) {
        this.englishword = englishword;
    }
    
    // ���{����Ԃ����\�b�h
    public String getJapaneseword() {
        return japaneseword;
    }
    
    // ���{����ݒ肷�郁�\�b�h
    public void setJapaneseword(String japaneseword) {
        this.japaneseword = japaneseword;
    }
}
