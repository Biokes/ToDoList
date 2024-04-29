function getHighest(ints){
    let output = []
    let max = Number.MIN_VALUE;
    for(let count =0; count< ints.length;count++){
        for(let counter =0; counter< ints.length;counter++){
            output.push(ints[counter]*ints[count]);
        }
    }
    output.forEach((num)=>  {if(num > max) max = num});
    if(ints.length === 1)
        return ints[0];
    return max;
}
