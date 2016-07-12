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

/**
 * Sum the elements of an array using the reduce function
 * @param array
 * @returns {*}
 */
let sumNumbersReduce = (array) => {

  return array.reduce((element, accumulator) => accumulator + element);

};

/**
 * Sum the elements of an array using recursion
 * @param array
 * @returns {*}
 */
let sumNumbersRecursive = (array) => {

    return array.length == 0 ? 0 : sumNumbersRecursive(array.splice(1,array.length)) + array[0];
};

export default {
  sumNumbersSequentially: sumNumberSequentially,
  sumNumbersReduce: sumNumbersReduce,
  sumNumbersRecursive: sumNumbersRecursive
};
