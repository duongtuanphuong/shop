$(document).ready(function () {
  $(".owl-1").owlCarousel({
    loop: true,
    items: 1,
  });
});

$(document).ready(function () {
  $(".owl-2").owlCarousel({
    loop: true,
    margin: 10,
    responsive: {
      0: {
        items: 1,
      },
      600: {
        items: 3,
      },
      1000: {
        items: 5,
      },
    },
  });
});


$(document).ready(function () {
  $(".owl-3").owlCarousel({
    loop: true,
    margin: 30,
    responsive: {
      0: {
        items: 1,
      },
      600: {
        items: 2,
      },
      1000: {
        items: 3,
      },
    },
  });
});





function hiddenFunction(){
  var a = document.querySelector(".menu-category");
  a.classList.toggle("category-hidden");
}

