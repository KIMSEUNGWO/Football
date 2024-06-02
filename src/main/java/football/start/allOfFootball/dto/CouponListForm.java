package football.start.allOfFootball.dto;

public record CouponListForm(
                            Long couponListId,
                            String couponName,
                            Integer couponDiscount,
                            String remainingTime,
                            String couponEndDate){

}
