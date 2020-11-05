<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html>
<html lang="zh-Hant-TW">

<head>
    <meta charset="UTF-8">
    <style>
        .clock * {
         box-sizing: border-box;
         margin: auto;
         padding: 0;
     }

     body .clock {
         align-items: center;
         /* background-color: #ffd54f; */
         display: flex;
         font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
         Oxygen-Sans, Ubuntu, Cantarell, "Helvetica Neue", sans-serif;
     }

     .clockContainer {
         color: #333;
         margin: 0 auto;
         text-align: center;
     }

     .clock h1 {
         font-weight: normal;
         letter-spacing: .125rem;
         text-transform: uppercase;
         text-align: center;
     }

     .clock li {
         line-height: 1.8em;
         display: inline-block;
         font-size: 1.5em;
         list-style-type: none;
         padding: 1em;
         text-transform: uppercase;
     }

     .clock li span {
         display: block;
         font-size: 4.5rem;
     }

     #clockMessage {
         display: none;
         font-size: 4rem;
     }

     .clock #content {
         padding: 1rem;
     }

     @media all and (max-width: 768px) {
         .clock h1 {
          font-size: 1.5rem;
      }
      .clock li {
          font-size: 1.125rem;
          padding: .75rem;
      }
      .clock li span {
          font-size: 3.375rem;
      }
  }
</style>
</head>

<body>
	<!-- partial:index.partial.html -->
	<div class="clockContainer clock">
		<div id="countdown">
			<ul>
				<li><span id="days"></span>days</li>
				<li><span id="hours"></span>Hours</li>
				<li><span id="minutes"></span>Minutes</li>
				<li><span id="seconds"></span>Seconds</li>
			</ul>
		</div>
		<div id="clockMessage">
			<div id="content">
				<h1 id="headline"></h1>
			</div>
		</div>
	</div>
	<!-- partial -->
	<script>
		(function() { //立即函示
			const second = 1000, minute = second * 60, hour = minute * 60, day = hour * 24;

			//          let bdEnd = "Oct 31, 2020 18:00:00",
			let bdEnd = '<c:choose><c:when test="${bdr.runningBd.bdNo eq bdNo}">${bdr.runningBd.bdDateEnd}</c:when><c:otherwise>0</c:otherwise></c:choose>', countDown = new Date(
                bdEnd).getTime(), clock=true;
			if (bdEnd !== 0) {
				 clock = setInterval(
                  function() {

                   let now = new Date().getTime(), distance = countDown
                   - now;

                   document.getElementById("days").innerText = Math
                   .floor(distance / (day)),
                   document.getElementById("hours").innerText = Math
                   .floor((distance % (day)) / (hour)),
                   document.getElementById("minutes").innerText = Math
                   .floor((distance % (hour))
                     / (minute)),
                   document.getElementById("seconds").innerText = Math
                   .floor((distance % (minute))
                     / second);

							//do something later when date is reached
							if (distance < 0) {
								let headline = document
                              .getElementById("headline"), countdown = document
                              .getElementById("countdown"), clockMessage = document
                              .getElementById("clockMessage");

                              headline.innerHTML = "競標結束! ";
                              countdown.style.display = "none";
                              clockMessage.style.display = "block";

                              bidOver = true;

                              clearInterval(clock);

                              clock = false;
                              console.log(clock);
                          } else {
                            bidOver = false;
                        }
							//seconds
						}, 500)
            }
            if (clock === false) {
              console.log('clocrei');
              location.reload();
          }else{
            console.log('clock fai');
        }

    }());

		//========================================
	</script>
</body>

</html>