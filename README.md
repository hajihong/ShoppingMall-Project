# ShoppingMall-Project
쇼핑몰 동시성 문제 스터디 프로젝트


여러 사람들이 제품을 구매할 때 동시성 문제 해결 과정과 각 해결 방식의 성능 체크를 위한 프로젝트

**재고 수량 동시성 이슈 해결**

- 멀티 스레드 환경에서 동시성 이슈 해결을 위한 다양한 기술력 중 최고의 기술을 선택하기 위해 성능 테스트 진행
- 성능 자동화 도구인 k6과 결과 데이터를 저장하고 분석하기 위한 influxdb 도입. 해당 데이터를 시각적으로 파악하기 위해 grafana 사용
- Pessimistic, Optimistic, Lettuce Lock, Redisson Lock을 각 동일한 환경에서 일정시간 동안 vuser를 증감소를 시켜 성능 테스트 진행
- Redisson이 요청 실패율 0%, 요청 완료 시간 23.35ms로 가장 빨랐으며 다른 기술보다 최고의 성능 지표를 나타냄

pemisitic(비관적락)

```java
checks.........................: 92.83% ✓ 100000    ✗ 7713
     data_received..................: 62 MB  46 kB/s
     data_sent......................: 22 MB  16 kB/s
     http_req_blocked...............: avg=1.95ms  min=0s          med=4.73µs  max=15.63s   p(90)=16.07µs p(95)=1.21ms
     http_req_connecting............: avg=1.94ms  min=0s          med=0s      max=15.63s   p(90)=0s      p(95)=1.12ms
     http_req_duration..............: avg=27.8ms  min=-7043691ns  med=11.08ms max=462.97ms p(90)=65.25ms p(95)=115.28ms
       { expected_response:true }...: avg=28.63ms min=61.32µs     med=11.08ms max=462.97ms p(90)=68.81ms p(95)=120.12ms
     http_req_failed................: 7.16%  ✓ 7713      ✗ 100000
     http_req_receiving.............: avg=109.9µs min=-20437704ns med=75.11µs max=34.83ms  p(90)=161.5µs p(95)=242.04µs
     http_req_sending...............: avg=37.57µs min=0s          med=23.04µs max=9.13ms   p(90)=70.54µs p(95)=97.67µs
     http_req_tls_handshaking.......: avg=0s      min=0s          med=0s      max=0s       p(90)=0s      p(95)=0s
     http_req_waiting...............: avg=27.65ms min=0s          med=10.94ms max=462.89ms p(90)=65.06ms p(95)=115.17ms
     http_reqs......................: 107713 80.795383/s
     iteration_duration.............: avg=1.06s   min=1s          med=1.01s   max=31s      p(90)=1.06s   p(95)=1.11s
     iterations.....................: 107711 80.793883/s
     vus............................: 1      min=1       max=125
     vus_max........................: 125    min=125     max=125
```

optimistic(낙관적락)

```java
checks.........................: 99.97% ✓ 83726     ✗ 17
     data_received..................: 6.1 MB 4.6 kB/s
     data_sent......................: 17 MB  13 kB/s
     http_req_blocked...............: avg=144.02µs min=0s          med=6.09µs  max=7.11s   p(90)=13.25µs  p(95)=20.65µs
     http_req_connecting............: avg=131.49µs min=-18475846ns med=0s      max=7.11s   p(90)=0s       p(95)=0s
     http_req_duration..............: avg=359.38ms min=52.4µs      med=9.11ms  max=59.96s  p(90)=478.45ms p(95)=1.86s
       { expected_response:true }...: avg=347.28ms min=52.4µs      med=9.11ms  max=58.75s  p(90)=474.1ms  p(95)=1.85s
     http_req_failed................: 0.02%  ✓ 17        ✗ 83726
     http_req_receiving.............: avg=129.82µs min=0s          med=92.71µs max=28.18ms p(90)=242.91µs p(95)=336.02µs
     http_req_sending...............: avg=58.07µs  min=8.44µs      med=31.61µs max=13.67ms p(90)=113.48µs p(95)=173.92µs
     http_req_tls_handshaking.......: avg=0s       min=0s          med=0s      max=0s      p(90)=0s       p(95)=0s
     http_req_waiting...............: avg=359.2ms  min=0s          med=8.96ms  max=59.96s  p(90)=478.08ms p(95)=1.86s
     http_reqs......................: 83743  63.452348/s
     iteration_duration.............: avg=1.36s    min=1s          med=1.01s   max=1m1s    p(90)=1.48s    p(95)=2.86s
     iterations.....................: 83743  63.452348/s
     vus............................: 1      min=1       max=125
     vus_max........................: 125    min=125     max=125
```

lettuce

```java
checks.........................: 99.99% ✓ 111567    ✗ 1
     data_received..................: 8.2 MB 6.2 kB/s
     data_sent......................: 22 MB  17 kB/s
     http_req_blocked...............: avg=38.84µs min=0s          med=5.1µs   max=62.6ms  p(90)=8.41µs   p(95)=12.41µs
     http_req_connecting............: avg=30.75µs min=0s          med=0s      max=62.48ms p(90)=0s       p(95)=0s
     http_req_duration..............: avg=20.06ms min=-14490125ns med=7.5ms   max=6.15s   p(90)=60.16ms  p(95)=64.22ms
       { expected_response:true }...: avg=20.06ms min=-14490125ns med=7.5ms   max=6.15s   p(90)=60.16ms  p(95)=64.22ms
     http_req_failed................: 0.00%  ✓ 1         ✗ 111567
     http_req_receiving.............: avg=87.73µs min=-20553993ns med=77.89µs max=24.05ms p(90)=145.89µs p(95)=189.58µs
     http_req_sending...............: avg=37.26µs min=0s          med=24.52µs max=13.78ms p(90)=66.1µs   p(95)=93.48µs
     http_req_tls_handshaking.......: avg=0s      min=0s          med=0s      max=0s      p(90)=0s       p(95)=0s
     http_req_waiting...............: avg=19.94ms min=0s          med=7.39ms  max=6.15s   p(90)=60.04ms  p(95)=64.08ms
     http_reqs......................: 111568 84.546201/s
     iteration_duration.............: avg=1.02s   min=1s          med=1s      max=31s     p(90)=1.06s    p(95)=1.06s
     iterations.....................: 111568 84.546201/s
     vus............................: 1      min=1       max=125
     vus_max........................: 125    min=125     max=125
```

redisson

```java
checks.........................: 100.00% ✓ 111269    ✗ 0
     data_received..................: 8.1 MB  6.2 kB/s
     data_sent......................: 22 MB   17 kB/s
     http_req_blocked...............: avg=38.18µs min=1.56µs  med=5.27µs  max=58.06ms p(90)=9.72µs   p(95)=15.3µs
     http_req_connecting............: avg=28.9µs  min=0s      med=0s      max=57.77ms p(90)=0s       p(95)=0s
     http_req_duration..............: avg=23.34ms min=40.63µs med=8.39ms  max=4.31s   p(90)=28.37ms  p(95)=55ms
       { expected_response:true }...: avg=23.34ms min=40.63µs med=8.39ms  max=4.31s   p(90)=28.37ms  p(95)=55ms
     http_req_failed................: 0.00%   ✓ 0         ✗ 111269
     http_req_receiving.............: avg=97.38µs min=9.57µs  med=78.88µs max=7.87ms  p(90)=168.02µs p(95)=233.02µs
     http_req_sending...............: avg=42.25µs min=6.59µs  med=25.49µs max=19.96ms p(90)=72.42µs  p(95)=105.48µs
     http_req_tls_handshaking.......: avg=0s      min=0s      med=0s      max=0s      p(90)=0s       p(95)=0s
     http_req_waiting...............: avg=23.2ms  min=0s      med=8.26ms  max=4.31s   p(90)=28.2ms   p(95)=54.76ms
     http_reqs......................: 111269  84.349941/s
     iteration_duration.............: avg=1.02s   min=1s      med=1s      max=5.34s   p(90)=1.02s    p(95)=1.05s
     iterations.....................: 111269  84.349941/s
     vus............................: 1       min=1       max=125
     vus_max........................: 125     min=125     max=125
```

<img width="1606" alt="스크린샷 2023-09-11 오후 7 25 08" src="https://github.com/hajihong/ShoppingMall-Project/assets/74098327/d978d67d-dabd-4a76-ab69-99c8b49db246">

