package miniproject.member.dto;

import lombok.Getter;

@Getter
public class MemberLightDto {
    private int memberPk;
    private String memberId;
    private String nickname;

    public MemberLightDto() {
    }

    public MemberLightDto(int memberPk, String memberId, String nickname) {
        this.memberPk = memberPk;
        this.memberId = memberId;
        this.nickname = nickname;
    }
}