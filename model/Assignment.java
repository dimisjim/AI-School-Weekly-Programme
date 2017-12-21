
package model;

public class Assignment {
    public final Lesson lesson;
    public final Teacher teacher;
    public final boolean empty;
    
    public Assignment() {
        lesson = null;
        teacher = null;
        empty = true;
    }
    public Assignment(Lesson lesson, Teacher teacher) {
        this.lesson = lesson;
        this.teacher = teacher;
        this.empty = false;
    }
}
