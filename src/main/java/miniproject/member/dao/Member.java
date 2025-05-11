package miniproject.member.dao;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class Member implements Serializable {
    private int memberPk;
    private String memberId;
    private String nickname;
    private String password;
    private int memberRoll;
    private LocalDateTime joinDate;
    private LocalDateTime editDate;
    private int editMemberIdx;
    private LocalDateTime lastLoginDate;
    private LocalDateTime lastLogoutDate;

    public Member(String memberId, String password) {
        this.memberId = memberId;
        this.password = password;
    }

    public Member(int memberPk, String memberId, String nickname, String password, int memberRoll, LocalDateTime joinDate, LocalDateTime editDate, int editMemberIdx, LocalDateTime lastLoginDate, LocalDateTime lastLogoutDate) {
        this.memberId = memberId;
        this.memberPk = memberPk;
        this.nickname = nickname;
        this.password = password;
        this.memberRoll = memberRoll;
        this.joinDate = joinDate;
        this.editDate = editDate;
        this.editMemberIdx = editMemberIdx;
        this.lastLoginDate = lastLoginDate;
        this.lastLogoutDate = lastLogoutDate;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberPk=" + memberPk +
                ", memberId='" + memberId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", memberRoll=" + memberRoll +
                ", joinDate=" + joinDate +
                ", editDate=" + editDate +
                ", editMemberIdx=" + editMemberIdx +
                ", lastLoginDate=" + lastLoginDate +
                ", lastLogoutDate=" + lastLogoutDate +
                '}';
    }
}
