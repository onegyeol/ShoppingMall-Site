package project.wideWebsite.exception;

//상품 주문 수량보다 현재 재고 수가 적을 때 exception 발생
public class OutOfStockException extends RuntimeException{


    public OutOfStockException(String message){
        super(message);
    }


}

