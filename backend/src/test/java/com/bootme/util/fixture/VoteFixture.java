package com.bootme.util.fixture;

import com.bootme.vote.dto.VoteRequest;

public class VoteFixture {

    public static String VALID_VOTE_TYPE_1 = "UPVOTE";
    public static String VALID_VOTE_TYPE_2 = "DOWNVOTE";
    public static String VALID_VOTE_TYPE_3 = "NONE";

    public static String VALID_VOTABLE_TYPE_1 = "POST";
    public static String VALID_VOTABLE_TYPE_2 = "POST_COMMENT";

    public static VoteRequest getPostUpvoteRequest() {
        return VoteRequest.builder()
                .voteType(VALID_VOTE_TYPE_1)
                .votableId(1L)
                .votableType(VALID_VOTABLE_TYPE_1)
                .memberId(1L)
                .build();
    }

    public static VoteRequest getPostDownvoteRequest() {
        return VoteRequest.builder()
                .voteType(VALID_VOTE_TYPE_2)
                .votableId(1L)
                .votableType(VALID_VOTABLE_TYPE_1)
                .memberId(1L)
                .build();
    }

}
