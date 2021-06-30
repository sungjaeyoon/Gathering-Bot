package com.kt.yoon.domain;

import com.kt.yoon.domain.form.ResponseSheetDto;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
public class JsonResponse {

    private int status;
    private String message;

    private JSONObject data;

    public JsonResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static JsonResponse userInfo(int status, User user, String token) {
        JsonResponse jsonResponse = new JsonResponse(status, "success");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", user.getId());
        jsonObject.put("email", user.getEmail());
        jsonObject.put("name", user.getName());
        jsonObject.put("position", user.getPosition());
        jsonObject.put("token", token);
        jsonResponse.data = jsonObject;
        return jsonResponse;
    }

    public static JsonResponse teamInfo(int status, List<User> users, String teamName) {
        JsonResponse jsonResponse = new JsonResponse(status, "success");
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (User user : users) {
            JSONObject temp = new JSONObject();
            temp.put("id", user.getId());
            temp.put("email", user.getEmail());
            temp.put("userName", user.getName());
            temp.put("teamName", teamName);
            temp.put("position", user.getPosition());
            jsonArray.add(temp);
        }
        jsonObject.put("users", jsonArray);
        jsonResponse.data = jsonObject;
        return jsonResponse;
    }

    public static JsonResponse sheetList(int status, Page<Sheet> sheetList) {
        JsonResponse jsonResponse = new JsonResponse(status, "success");
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<Sheet> content = sheetList.getContent();
        for (Sheet sheet : content) {
            JSONObject temp = new JSONObject();
            temp.put("id", sheet.getId());
            temp.put("createdDate", sheet.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            temp.put("shareType", sheet.getShareType());
            temp.put("sheetStatus", sheet.getSheetStatus());
            temp.put("title", sheet.getTitle());
            temp.put("token", sheet.getToken());
            jsonArray.add(temp);
        }
        jsonObject.put("sheetList", jsonArray);
        jsonObject.put("totalElements", sheetList.getTotalElements());
        jsonObject.put("totalPages", sheetList.getTotalPages());
        jsonResponse.data = jsonObject;
        return jsonResponse;
    }

    public static JsonResponse responseList(int status, List<Response> responseList, Sheet sheet) {
        JsonResponse jsonResponse = new JsonResponse(status, "success");
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Response response : responseList) {
            JSONObject temp = new JSONObject();
            temp.put("id", response.getId());
            temp.put("email", response.getEmail());
            temp.put("requestStatus", response.getRequestStatus());
            temp.put("response", response.getResponse());
            if (response.getResponseDate() == null) {
                temp.put("responseDate", "");
            } else {
                temp.put("responseDate", response.getResponseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
            temp.put("teamName", response.getTeamName());
            temp.put("userName", response.getUserName());
            temp.put("position", response.getPosition());
            jsonArray.add(temp);
        }
        jsonObject.put("responseList", jsonArray);
        jsonObject.put("title", sheet.getTitle());
        jsonObject.put("question", sheet.getQuestion());
        jsonObject.put("example", sheet.getExample());
        jsonObject.put("colNum", responseList.size());
        jsonResponse.data = jsonObject;
        return jsonResponse;
    }

    public static JsonResponse sheetDetail(int status, List<Response> responseList, Sheet sheet) {
        JsonResponse jsonResponse = new JsonResponse(status, "success");
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Response response : responseList) {
            JSONObject temp = new JSONObject();
            temp.put("id", response.getId());
            temp.put("email", response.getEmail());
            if (response.getLastSendMailDate() == null) {
                temp.put("lastSendMailDate", "");
            } else {
                temp.put("lastSendMailDate", response.getLastSendMailDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
            if (response.getModifiedDate() == null) {
                temp.put("modifiedDate", "");
            } else {
                temp.put("modifiedDate", response.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
            if (response.getResponseDate() == null) {
                temp.put("responseDate", "");
            } else {
                temp.put("responseDate", response.getResponseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }

            temp.put("position", response.getPosition());
            temp.put("requestStatus", response.getRequestStatus());
            temp.put("response", response.getResponse());
            temp.put("teamName", response.getTeamName());
            temp.put("userName", response.getUserName());
            jsonArray.add(temp);
        }
        jsonObject.put("responseList", jsonArray);
        jsonObject.put("id", sheet.getId());
        jsonObject.put("content", sheet.getContent());
        jsonObject.put("createdDate", sheet.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        jsonObject.put("email", sheet.getEmail());
        jsonObject.put("example", sheet.getExample());
        jsonObject.put("finishedDate", sheet.getFinishedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        if (sheet.getLastSendMailDate() == null) {
            jsonObject.put("lastSendMailDate", "");
        } else {
            jsonObject.put("lastSendMailDate", sheet.getLastSendMailDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        jsonObject.put("position", sheet.getPosition());
        jsonObject.put("question", sheet.getQuestion());
        jsonObject.put("shareType", sheet.getShareType());
        jsonObject.put("sheetStatus", sheet.getSheetStatus());
        jsonObject.put("teamName", sheet.getTeamName());
        jsonObject.put("title", sheet.getTitle());
        jsonObject.put("token", sheet.getToken());
        jsonObject.put("userName", sheet.getUserName());
        jsonObject.put("colNum", sheet.getColNum());
        jsonResponse.data = jsonObject;
        return jsonResponse;
    }

    public static JsonResponse UserResponseList(int status, Page<ResponseSheetDto> userResponseList) {
        JsonResponse jsonResponse = new JsonResponse(status, "success");
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (ResponseSheetDto userResponse : userResponseList) {
            JSONObject temp = new JSONObject();
            temp.put("responseId",userResponse.getResponseId());
            temp.put("sheetId",userResponse.getSheetId());
            temp.put("requestStatus",userResponse.getRequestStatus());
            temp.put("sheetStatus",userResponse.getSheetStatus());
            temp.put("title",userResponse.getTitle());
            temp.put("responseToken",userResponse.getResponseToken());
            temp.put("sheetToken",userResponse.getSheetToken());
            temp.put("shareType",userResponse.getShareType());
            if (userResponse.getResponseDate() == null) {
                temp.put("responseDate", "");
            } else {
                temp.put("responseDate", userResponse.getResponseDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }

            jsonArray.add(temp);
            jsonObject.put("totalElements", userResponseList.getTotalElements());
            jsonObject.put("totalPages", userResponseList.getTotalPages());
        }
        jsonObject.put("responseList", jsonArray);
        jsonResponse.data = jsonObject;
        return jsonResponse;
    }


    /***
     * 정상 응답
     */
//    public JsonResponse(int status, String message) {
//        super();
//        this.put("status",status);
//        this.put("message",message);
//    }

    /***
     * 로그인 응답
     * @param status
     * @param message
     * @param member
     * @param token
     */
//    public JsonResponse(int status, String message, Member member,String token) {
//        super();
//        this.put("status",status);
//        this.put("message",message);
//        this.put("id", member.getId());
//        this.put("email", member.getEmail());
//        this.put("username", member.getName());
//        this.put("teamName", member.getTeamName());
//        this.put("position", member.getPosition());
//        this.put("token", token);
//    }
//
//    /***
//     * 유저 목록 조회 응답
//     * @param status
//     * @param message
//     * @param memberList
//     */
//    public JsonResponse(int status, String message, List<Member> memberList) {
//        super();
//        JSONArray jsonArray = new JSONArray();
//        for (Member member : memberList) {
//            JSONObject jsonMember = new JSONObject();
//            jsonMember.put("id", member.getId());
//            jsonMember.put("name", member.getName());
//            jsonMember.put("email", member.getEmail());
//            jsonMember.put("position", member.getPosition());
//            jsonMember.put("teamName", member.getTeamName());
//            jsonArray.add(jsonMember);
//        }
//        this.put("users",jsonArray);
//        this.put("status",status);
//        this.put("message",message);
//    }
//
//    /***
//     * 응답 페이지 데이터 조회 응답
//     * @param status
//     * @param message
//     * @param sheet
//     * @param member
//     */
//    public JsonResponse(int status, String message, Sheet sheet, Member member){
//        super();
//        this.put("memberId", member.getId());
//        this.put("name", member.getName());
//        this.put("position", member.getPosition());
//        this.put("teamName", member.getTeamName());
//        this.put("content", sheet.getContent());
//        this.put("question", sheet.getQuestion());
//        this.put("example", sheet.getExample());
//        this.put("status",status);
//        this.put("message",message);
//    }
//
//    /***
//     * 유저id 로 시트 조회 응답
//     * @param status
//     * @param message
//     * @param sheetList
//     * @param page
//     */
//    public JsonResponse(int status, String message, List<Sheet> sheetList, int page){
//        super();
//        JSONArray jsonArray = new JSONArray();
//        for (Sheet sheet : sheetList) {
//            JSONObject jsonObjectSheet = new JSONObject();
//            jsonObjectSheet.put("id", sheet.getId());
//            jsonObjectSheet.put("title", sheet.getTitle());
//            jsonObjectSheet.put("content", sheet.getContent());
//            jsonObjectSheet.put("question", sheet.getQuestion());
//            jsonObjectSheet.put("createdDate", sheet.getCreatedDate());
//            jsonObjectSheet.put("finishedDate", sheet.getFinishedDate());
//            jsonObjectSheet.put("sheetStatus", sheet.getSheetStatus());
//            jsonArray.add(jsonObjectSheet);
//        }
//        this.put("sheets",jsonArray);
//        this.put("page",page);
//        this.put("status",status);
//        this.put("message",message);
//    }
//
//    /***
//     * 시트 상세 조회
//     * @param status
//     * @param message
//     * @param sheet
//     * @param memberSheets
//     */
//    public JsonResponse(int status, String message, Sheet sheet, List<MemberSheet> memberSheets){
//        super();
//        JSONObject jsonObjectSheet = new JSONObject();
//        jsonObjectSheet.put("content", sheet.getContent());
//        jsonObjectSheet.put("title", sheet.getTitle());
//        jsonObjectSheet.put("question", sheet.getQuestion());
//        jsonObjectSheet.put("example", sheet.getExample());
//        jsonObjectSheet.put("colNum", sheet.getColNum());
//        jsonObjectSheet.put("createdDate", sheet.getCreatedDate());
//        jsonObjectSheet.put("status", sheet.getSheetStatus());
//        this.put("sheet", jsonObjectSheet);
//
//        JSONArray jsonArray = new JSONArray();
//        for (MemberSheet memberSheet : memberSheets) {
//            JSONObject jsonObjectMemberSheet = new JSONObject();
//            jsonObjectMemberSheet.put("id", memberSheet.getId());
//            jsonObjectMemberSheet.put("modifiedDate", memberSheet.getModifiedDate());
//            jsonObjectMemberSheet.put("requestStatus", memberSheet.getRequestStatus());
//            jsonObjectMemberSheet.put("response", memberSheet.getResponse());
//            jsonObjectMemberSheet.put("responseDate", memberSheet.getResponseDate());
//            jsonObjectMemberSheet.put("email", memberSheet.getMember().getEmail());
//            jsonObjectMemberSheet.put("name", memberSheet.getMember().getName());
//            jsonObjectMemberSheet.put("position", memberSheet.getMember().getPosition());
//            jsonObjectMemberSheet.put("teamName", memberSheet.getMember().getTeamName());
//            jsonArray.add(jsonObjectMemberSheet);
//        }
//        this.put("memberSheet", jsonArray);
//        this.put("status",status);
//        this.put("message",message);
//    }

}
