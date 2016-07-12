import assert from 'assert';
import lib from '../lib';

let inputArray = [1, 2, 3, 4, 5, 1, 2, 3, 4, 5];
const expected = 30;

describe('task-node-2', function () {

  it('should sum number sequentially!', function () {

    let actual =  lib.sumNumbersSequentially(inputArray);

    assert.equal(expected, actual,'we expected the sum is correct.');
  });

  it('should sum number using reduce!', function () {

    let actual =  lib.sumNumbersReduce(inputArray);

    assert.equal(expected, actual,'we expected the sum is correct.');
  });
});
