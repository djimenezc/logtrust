let sumNumberSequentially = (array) => {

  let sum = 0;

  array.forEach((element) => {
    sum += element;
  });

  return sum;
};

export default {

  sumNumberSequentially: sumNumberSequentially
};
