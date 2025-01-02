# Flight Booking Case
## Local Requirements
-Java 11
-Spring Boot 2.17.8
-PostgreSql

Or

Docker Desktop
## Setup
```sh
git clone https://github.com/cagdasdemirer/flight-booking.git
docker-compose --build
```
## End-to-End Tests
- http://localhost:8080/swagger-ui/index.html
- api/flight-booking.http

## Summary
Case'e Model dizaynlarıyla başladım ve in-memory CRUD operasyonlarıyla devam ettim. Senaryo sayısı arttıkça custom exception handler implemente ettim ve repository'i jpa repository e dönüştürüp database bağlantısını sağladım. Başlangıçta testin kolay olması için bir Data Loader ekledim. End-to-end testlerde sıkıntı çıkaran yerleri fixleyip swagger-ui ekledim. Seat Booking kısmında eş zamanlılık ve sanaryo kaçırmamak için Optimistic Locking ve async featurelaerdan yardım aldım. Sonrasında az sayıda da olsa unit test yazıp uygulamayı postgre database ile birlikte çalışacak şekilde dockerize ettim. Totalde 12 saate yakın vakit harcadım. İstediğim kadar vakit ayıramasam da genel gereklilikleri karşılayan bir proje çıktı ortaya.