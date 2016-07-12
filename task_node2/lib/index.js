/**
 * Sum the elements of an array using the each function
 * @param array
 * @returns {number}
 */
let sumNumberSequentially = (array) => {

  let sum = 0;

  array.forEach((element) => {
    sum += element;
  });

  return sum;
};

let sumNumbersReduce = (array) => {

  return array.reduce((element, accumulator) => {
    
    return accumulator + element;
  });

};

export default {

  sumNumbersSequentially: sumNumberSequentially,
  sumNumbersReduce: sumNumbersReduce  
  
};
