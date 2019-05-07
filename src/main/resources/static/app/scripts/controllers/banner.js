'use strict';

app.controller('appController', function($scope) {
  $scope.images = [
    {
      src: "./images/jobs/higgidy_1.jpg",
      alt: "image 1",
      link: "http://www.higgidy.co.uk"
    },
    {
      src: "./images/jobs/higgidy_2.jpg",
      alt: "image 2",
      link: "http://www.higgidy.co.uk"
    },
    {
      src: "./images/jobs/higgidy_3.jpg",
      alt: "image 3",
      link: "http://www.higgidy.co.uk"
    },
    {
      src: "./images/jobs/higgidy_3.jpg",
      alt: "image 3",
      link: "http://www.higgidy.co.uk"
    }
  ]
  $scope.ind=0;
  var increment =function(i){
	      i=i+1;
	  console.log(i);
	  return i;
	  
  }
  
  
});