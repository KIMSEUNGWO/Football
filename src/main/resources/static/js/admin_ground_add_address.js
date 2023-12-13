function searchAddress() {
    new daum.Postcode({
        oncomplete: function(data) {

            var roadAddr = data.roadAddress; // 도로명 주소 변수
            document.querySelector('input[name="fieldAddress"]').value = roadAddr;
            
        }
    }).open();
}