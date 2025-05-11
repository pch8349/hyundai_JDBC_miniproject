package miniproject.member.dto;

import lombok.Getter;
import lombok.Setter;
import miniproject.member.dao.Member;

@Getter
@Setter
public class MemberDto {
    private Member member;
    private int state;

    public MemberDto(Member member, int state) {
        this.member = member;
        this.state = state;
    }
}
