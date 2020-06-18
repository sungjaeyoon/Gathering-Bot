package com.kt.yoon.domain;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class JsonResponse extends JSONObject {

    /***
     * 정상 응답
     * @param status
     * @param message
     */
    public JsonResponse(int status, String message) {
        super();
        this.put("status",status);
        this.put("message",message);
    }

    /***
     * 로그인 응답
     * @param status
     * @param message
     * @param member
     * @param token
     */
    public JsonResponse(int status, String message, Member member,String token) {
        super();
        this.put("status",status);
        this.put("message",message);
        this.put("id", member.getId());
        this.put("email", member.getEmail());
        this.put("username", member.getName());
        this.put("teamName", member.getTeamName());
        this.put("position", member.getPosition());
        this.put("token", token);
    }

    /***
     * 유저 목록 조회 응답
     * @param status
     * @param message
     * @param memberList
     */
    public JsonResponse(int status, String message, List<Member> memberList) {
        super();
        JSONArray jsonArray = new JSONArray();
        for (Member member : memberList) {
            JSONObject jsonMember = new JSONObject();
            jsonMember.put("id", member.getId());
            jsonMember.put("name", member.getName());
            jsonMember.put("email", member.getEmail());
            jsonMember.put("position", member.getPosition());
            jsonMember.put("teamName", member.getTeamName());
            jsonArray.add(jsonMember);
        }
        this.put("users",jsonArray);
        this.put("status",status);
        this.put("message",message);
    }

    /***
     * 응답 페이지 데이터 조회 응답
     * @param status
     * @param message
     * @param sheet
     * @param member
     */
    public JsonResponse(int status, String message, Sheet sheet, Member member){
        super();
        this.put("memberId", member.getId());
        this.put("name", member.getName());
        this.put("position", member.getPosition());
        this.put("teamName", member.getTeamName());
        this.put("content", sheet.getContent());
        this.put("question", sheet.getQuestion());
        this.put("example", sheet.getExample());
        this.put("status",status);
        this.put("message",message);
    }

    /***
     * 유저id 로 시트 조회 응답
     * @param status
     * @param message
     * @param sheetList
     * @param userId
     */
    public JsonResponse(int status, String message, List<Sheet> sheetList,String userId){
        super();
        JSONArray jsonArray = new JSONArray();
        for (Sheet sheet : sheetList) {
            JSONObject jsonObjectSheet = new JSONObject();
            jsonObjectSheet.put("id", sheet.getId());
            jsonObjectSheet.put("title", sheet.getTitle());
            jsonObjectSheet.put("content", sheet.getContent());
            jsonObjectSheet.put("question", sheet.getQuestion());
            jsonObjectSheet.put("createdDate", sheet.getCreatedDate());
            jsonObjectSheet.put("finishedDate", sheet.getFinishedDate());
            jsonObjectSheet.put("sheetStatus", sheet.getSheetStatus());
            jsonArray.add(jsonObjectSheet);
        }
        this.put("sheets",jsonArray);
        this.put("userId",userId);
        this.put("status",status);
        this.put("message",message);
    }

    /***
     * 시트 상세 조회
     * @param status
     * @param message
     * @param sheet
     * @param memberSheets
     */
    public JsonResponse(int status, String message, Sheet sheet, List<MemberSheet> memberSheets){
        super();
        JSONObject jsonObjectSheet = new JSONObject();
        jsonObjectSheet.put("content", sheet.getContent());
        jsonObjectSheet.put("title", sheet.getTitle());
        jsonObjectSheet.put("question", sheet.getQuestion());
        jsonObjectSheet.put("example", sheet.getExample());
        jsonObjectSheet.put("colNum", sheet.getColNum());
        jsonObjectSheet.put("createdDate", sheet.getCreatedDate());
        jsonObjectSheet.put("status", sheet.getSheetStatus());
        this.put("sheet", jsonObjectSheet);

        JSONArray jsonArray = new JSONArray();
        for (MemberSheet memberSheet : memberSheets) {
            JSONObject jsonObjectMemberSheet = new JSONObject();
            jsonObjectMemberSheet.put("id", memberSheet.getId());
            jsonObjectMemberSheet.put("modifiedDate", memberSheet.getModifiedDate());
            jsonObjectMemberSheet.put("requestStatus", memberSheet.getRequestStatus());
            jsonObjectMemberSheet.put("response", memberSheet.getResponse());
            jsonObjectMemberSheet.put("responseDate", memberSheet.getResponseDate());
            jsonObjectMemberSheet.put("email", memberSheet.getMember().getEmail());
            jsonObjectMemberSheet.put("name", memberSheet.getMember().getName());
            jsonObjectMemberSheet.put("position", memberSheet.getMember().getPosition());
            jsonObjectMemberSheet.put("teamName", memberSheet.getMember().getTeamName());
            jsonArray.add(jsonObjectMemberSheet);
        }
        this.put("memberSheet", jsonArray);
        this.put("status",status);
        this.put("message",message);
    }

}
