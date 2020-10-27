        var memId = $("#memId").val();
        var bdNo = $("#bdNo").val();
		var top1;
		var top2;
		var top3;
        var bidOver = true;

        function isLogin() {
            if (memId.trim().length === 0) {
                Swal.fire(
                    'Not a Member?',
                    'Please Login First',
                    'question'
                )
                return false;
            } else {
                return true;
            }
        }

        (function newBid() {

            $('#submitBid').on("click", function(e) {
                $('#submitBid').attr('disabled', 'true');
                setInterval(function() {
                    $('#submitBid').removeAttr('disabled');
                }, 500);

                if (!isLogin() || bidOver) {
                    return;
                }
                console.log('Login Checked');
                console.log(memId);
                // e.preventDefault();
                let currentPrice = $('#currentPrice').html();
                let bid = $('#submitBidPrice').val();
                let reg = /^\d+$/;

                //=====================================================================================
                if (!bid.match(reg)) {
                    //========================sweetAlert=================
                    let timerInterval
                    Swal.fire({
                        title: 'Auto close alert!',
                        html: 'Please enter correct Bid Number!',
                        timer: 2000,
                        timerProgressBar: true,
                        willOpen: () => {
                            Swal.showLoading()
                            timerInterval = setInterval(() => {
                                const content = Swal.getContent()
                                if (content) {
                                    const b = content.querySelector('b')
                                    if (b) {
                                        b.textContent = Swal.getTimerLeft()
                                    }
                                }
                            }, 100)
                        },
                        onClose: () => {
                            clearInterval(timerInterval)
                        }
                    }).then((result) => {
                        /* Read more about handling dismissals below */
                        if (result.dismiss === Swal.DismissReason.timer) {
                            console.log('I was closed by the timer')
                        }
                    })
                    //========================/sweetAlert=================
                    return;
                }
                //=====================================================================================
                currentPrice = parseInt(currentPrice);
                bid = parseInt(bid);

                if (bid > currentPrice && bid > 15000) {

                    //========================sweetAlert=================
                    const swalWithBootstrapButtons = Swal.mixin({
                        customClass: {
                            confirmButton: 'btn btn-success',
                            cancelButton: 'btn btn-danger'
                        },
                        buttonsStyling: false
                    })

                    swalWithBootstrapButtons.fire({
                        title: 'Your price is: $ ' + bid,
                        text: "You won't be able to revert this!",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonText: 'Yes, Submit!',
                        cancelButtonText: 'No, cancel!',
                        reverseButtons: true
                    }).then((result) => {
                        if (result.isConfirmed) {
                            swalWithBootstrapButtons.fire(
                                'Submit!',
                                'success'
                            )
                            $.ajax({
                                method: "post",
                                url: "/EA103G1/biddingPage/BdPageServlet",
                                data: { action: "newBid", bid: bid, memId: memId, bdNo, bdNo },
                                success: function(dataReturn) {
                                    $('#currentPrice').text(dataReturn);
                                },
                                error: function() {

                                    //========================sweetAlert=================
                                    Swal.fire(
                                        'The Internet?',
                                        'Please check your connection',
                                        'question'
                                    )
                                    //========================/sweetAlert=================
                                }
                            })
                        } else if (
                            /* Read more about handling dismissals below */
                            result.dismiss === Swal.DismissReason.cancel
                        ) {
                            swalWithBootstrapButtons.fire(
                                'Cancelled',
                                'rolledBack'
                            )
                            return;
                        }
                    });
                    //========================/sweetAlert=================
                    return;
                }


                //=====================================================================================
                if (bid > currentPrice) {
                    console.log("memId" + memId);
                    $.ajax({
                        method: "post",
                        url: "/EA103G1/biddingPage/BdPageServlet",
                        data: { action: "newBid", bid: bid, memId: memId, bdNo: bdNo },
                        success: function(dataReturn) {
                            $('#currentPrice').text(bid);
                        },
                        error: function() {
                            Swal.fire(
                                'The Internet?',
                                'Please check your connection',
                                'question'
                            )
                        }
                    });
                } else {
                    //========================sweetAlert=================
                    let timerInterval
                    Swal.fire({
                        title: 'Auto close alert!',
                        html: 'Please enter higher Bid !',
                        timer: 2000,
                        timerProgressBar: true,
                        willOpen: () => {
                            Swal.showLoading()
                            timerInterval = setInterval(() => {
                                const content = Swal.getContent()
                                if (content) {
                                    const b = content.querySelector('b')
                                    if (b) {
                                        b.textContent = Swal.getTimerLeft()
                                    }
                                }
                            }, 100)
                        },
                        onClose: () => {
                            clearInterval(timerInterval)

                        }
                    }).then((result) => {
                        /* Read more about handling dismissals below */
                        if (result.dismiss === Swal.DismissReason.timer) {
                            console.log('I was closed by the timer')
                        }
                    })
                    return;
                    //========================/sweetAlert=================
                }
            });

            //================================================================

            $('#submitBidPrice').keypress(function(e) {
                if (e.keyCode == 13) {
                    e.preventDefault();
                    return false;
                }
            });

            $('#plus100').on("click", function(e) {
                e.preventDefault();
                $('#plus100').attr('disabled', 'true');
                setInterval(function() {
                    $('#plus100').removeAttr('disabled');
                }, 500);

                if (!isLogin() || bidOver) {
                    return;
                }

                let currentPrice = parseInt($('#currentPrice').html());




                let bid = currentPrice + 100;
                if (bid > currentPrice && bid > 15000) {

                    //========================sweetAlert=================
                    const swalWithBootstrapButtons = Swal.mixin({
                        customClass: {
                            confirmButton: 'btn btn-success',
                            cancelButton: 'btn btn-danger'
                        },
                        buttonsStyling: false
                    })

                    swalWithBootstrapButtons.fire({
                        title: 'Your price is: $ ' + bid,
                        text: "You won't be able to revert this!",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonText: 'Yes, Submit!',
                        cancelButtonText: 'No, cancel!',
                        reverseButtons: true
                    }).then((result) => {
                        if (result.isConfirmed) {
                            swalWithBootstrapButtons.fire(
                                'Submit!',
                                'success'
                            )
                            $.ajax({
                                method: "post",
                                url: "/EA103G1/biddingPage/BdPageServlet",
                                data: { action: "newBid", bid: bid, memId: memId, bdNo, bdNo },
                                success: function(dataReturn) {
                                    $('#currentPrice').text(dataReturn);
                                },
                                error: function() {

                                    //========================sweetAlert=================
                                    Swal.fire(
                                        'The Internet?',
                                        'Please check your connection',
                                        'question'
                                    )
                                    //========================/sweetAlert=================
                                }
                            })
                        } else if (
                            /* Read more about handling dismissals below */
                            result.dismiss === Swal.DismissReason.cancel
                        ) {
                            swalWithBootstrapButtons.fire(
                                'Cancelled',
                                'rolledBack'
                            )
                            return;
                        }
                    });
                    //========================/sweetAlert=================
                    return;
                }

                //=====================================================================================
                if (bid > currentPrice) {
                    console.log("memId" + memId);
                    $.ajax({
                        method: "post",
                        url: "/EA103G1/biddingPage/BdPageServlet",
                        data: { action: "newBid", bid: bid, memId: memId, bdNo: bdNo },
                        success: function(dataReturn) {
                            $('#currentPrice').text(bid);
                        },
                        error: function() {
                            Swal.fire(
                                'The Internet?',
                                'Please check your connection',
                                'question'
                            )
                        }
                    });
                } else {
                    //========================sweetAlert=================
                    let timerInterval
                    Swal.fire({
                        title: 'Auto close alert!',
                        html: 'Please enter higher Bid !',
                        timer: 2000,
                        timerProgressBar: true,
                        willOpen: () => {
                            Swal.showLoading()
                            timerInterval = setInterval(() => {
                                const content = Swal.getContent()
                                if (content) {
                                    const b = content.querySelector('b')
                                    if (b) {
                                        b.textContent = Swal.getTimerLeft()
                                    }
                                }
                            }, 100)
                        },
                        onClose: () => {
                            clearInterval(timerInterval)

                        }
                    }).then((result) => {
                        /* Read more about handling dismissals below */
                        if (result.dismiss === Swal.DismissReason.timer) {
                            console.log('I was closed by the timer')
                        }
                    })
                    return;
                    //========================/sweetAlert=================
                }

            });

        }());


        // ================================================================

        (function() {

            let checkBidEnd = setInterval(function() {

                if (bidOver) {

                    document.getElementById("submitBid").disabled = true;
                    document.getElementById("submitBidPrice").disabled = true;
                    document.getElementById("plus100").disabled = true;
                    updateBid();
                    console.log('Bid End!');

                    $('#winningForm').css('display', 'block');
                    $('#modalWrapper').css('display', 'block');
                    clearInterval(checkBidEnd);

                } else {

                    if (bdNo.trim().length === 0) {
                        console.log("No bdNo");
                        Swal.fire(
                            'The Internet?',
                            'Please check your connection',
                            'question'
                        )
                        return;
                    }
                    updateBid();
                }
            }, 1000)

        }());

        // =============================================
        function updateBid() {
            $.ajax({
                method: "post",
                url: "/EA103G1/biddingPage/BdPageServlet",
                data: { action: "topBidders", bdNo: bdNo },
                success: function(dataReturn) {

                    var bidData = JSON.parse(dataReturn);

                    if (typeof(bidData.top1name) !== "undefined") {
	
						console.log(bidData.top1name);
                        $('#top1 span').html(bidData.top1name);
                        $('#top2 span').html(bidData.top2name);
                        $('#top3 span').html(bidData.top3name);

                        $('#currentPrice').html(bidData.price1);
                        $('#price1').html('$' + bidData.price1);
                        $('#price2').html('$' + bidData.price2);
                        $('#price3').html('$' + bidData.price3);
                        $('#numberOfBids').html(bidData.numberOfBids);
                        // ============================
                    }
                },
                error: function() {
                    Swal.fire(
                        'The Internet?',
                        'Please check your connection',
                        'question'
                    )
                    return;
                },
            });
        }
        //========================================以下寫在贏家表單=======

        $(".checkout").on("click", function(e) {
            e.preventDefault();

            Swal.fire({
                title: '確認付款',
                text: '資料填寫正確了嗎?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: '立即付款!'
            }).then((result) => {
                if (result.isConfirmed) {
                    console.log('confirmed');
                    //pending=======================
                    var data = $('#checkoutForm').serialize();
                    $.ajax({
                        type: "post",
                        url: "EA103G1/biddingPage/BdPageServlet",
                        data: data,
                        success: function(d) {
                            if (d === "complete") {
                                //==================sweetAlert==============
                                $('.modal-body').html('<div>Pending...</div>');
                                var temp = $('.modal-body').html();
                                setTimeout(function() {
                                    $('.modal-body').html(temp);
                                    Swal.fire({
                                        title: '恭喜您',
                                        text: '付款成功',
                                        icon: 'success',
                                        confirmButtonColor: '#3085d6',
                                        cancelButtonColor: '#d33',
                                        confirmButtonText: '確認'
                                    }).then((result2) => {
                                        if (result2.isConfirmed) {
                                            $('#modalButton').click();
                                            location.reload();
                                        }
                                    })
                                }, 4000);
                            } else {
                                console.log('failed to complete');
                            }
                            //==================sweetAlert==============
                        },
                        error: function() {
                            Swal.fire(
                                'The Internet?',
                                'Please check your connection',
                                'question'
                            )
                            return;
                        }
                    })
                    //pending=======================
                }
            })

            // $('#checkout').click();
        });


        function getPrice() {
            $('#checkoutPrice').html($('#price1').html());
        };


        $("#pscroll1").on("click", function() {
            var personalInfo = document.getElementById("personal-info");
            personalInfo.scrollIntoView({ behavior: 'smooth', block: 'center' });
        })

        $("#pscroll2").on("click", function() {
            var shippingInfo = document.getElementById("shipping-info");
            shippingInfo.scrollIntoView({ behavior: 'smooth', block: 'center' });
        })

        $("#pscroll3").on("click", function() {
            var myOrders = document.getElementById("my-orders");
            myOrders.scrollIntoView({ behavior: 'smooth', block: 'center' });
        })