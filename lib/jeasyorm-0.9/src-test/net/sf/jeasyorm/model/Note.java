package net.sf.jeasyorm.model;

public class Note {

    private Long id;
    private String content;
    private byte[] attachment;

    public Note() { }
    
    public Note(String content, byte[] attachment) {
        this.content = content;
        this.attachment = attachment;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public byte[] getAttachment() { return attachment; }
    public void setAttachment(byte[] attachment) { this.attachment = attachment; }  
    
}
