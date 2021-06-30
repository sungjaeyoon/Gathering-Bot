package com.kt.yoon.trash;

//@Entity
//@Getter
public class Member /**implements UserDetails **/{
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "member_id")
//    private Long id;
//
//    @Column(nullable = false, length = 30)
//    private String name;
//
//    @Column(nullable = false, unique = true, length = 100)
//    private String email;
//
//    @JsonIgnore
//    @Column(nullable = false, length = 300)
//    private String password;
//
//    private String position;
//
//    private String teamName;
//
//    @JsonIgnore
//    @Column
//    private int failCount;
//
//    @Enumerated(value = EnumType.STRING)
//    private UserStatus userStatus;
//
//    @ElementCollection(fetch = FetchType.EAGER)
//    private List<String> roles = new ArrayList<>();
//
//    @JsonIgnore
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.roles.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//    }
//
//    @JsonIgnore
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//
//    //== 생성 메소드==//
//
//    public Member() {
//    }
//
//    public Member(String name, String email, String password, String position, String teamName) {
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.position = position;
//        this.teamName = teamName;
//        this.failCount =0;
//        this.userStatus = UserStatus.ACTIVE;
//    }
//
//    public static Member createMember(MemberForm memberForm) {
//        return new Member(memberForm.getName(), memberForm.getEmail(), memberForm.getPassword(), memberForm.getPosition(), memberForm.getTeamName());
//    }
//
//    //== setter ==//
//    public void updateUser(MemberForm memberForm){
//        this.name = memberForm.getName();
//        this.email = memberForm.getEmail();
//        this.password = memberForm.getPassword();
//        this.position = memberForm.getPosition();
//        this.teamName = memberForm.getTeamName();
//    }
//
//
//    //== 비지니스 로직 ==//
//    public void addFailCount(){
//        this.failCount++;
//    }
//
//    public void initFailCount(){
//        this.failCount=0;
//    }
//
//    public boolean isFailCountOver() {
//        if (this.failCount == 3) {
//            return true;
//        }
//        return false;
//    }
//
//    public void blockUser(){
//        this.userStatus = UserStatus.BLOCK;
//        this.failCount=0;
//    }
//
//    public void activeUser(){
//        this.userStatus = UserStatus.ACTIVE;
//    }
//
//    public boolean isUserBlocked() {
//        if (this.userStatus == UserStatus.BLOCK) {
//            return true;
//        }
//        return false;
//    }
}
