function weeksBetween(d1, d2) {
    return Math.ceil((d2 - d1) / (7 * 24 * 60 * 60 * 1000));
}

function getCentralTime() {
    let currentDate = new Date();
    currentDate.setHours(currentDate.getHours() + 6 - (currentDate.getTimezoneOffset() / 60))
    return currentDate;
}

export function  calcWeek(){
    let currentDate = new Date();
    // central time = 360 / 60 = 6 
    return weeksBetween(new Date(2020, 8, 8, 4 + 6 - (currentDate.getTimezoneOffset() / 60)), getCentralTime());
}

export function lockWeek() {
    // get date into central time 
    let currentDateCentralTime = getCentralTime()

    // see if its between thursday @7 and monday @10 
    // can we use difference in weeks/ 
    //1. tuesday @3am
    let startWeekCentralTime = new Date(2020, 8, 8, 4 + 6 - (currentDateCentralTime.getTimezoneOffset() / 60))

    //2 thursday @7pm
    let endWeekCentralTime = new Date(2020, 8, 10, 19 + 6 - (currentDateCentralTime.getTimezoneOffset() / 60))

    return weeksBetween(startWeekCentralTime, currentDateCentralTime) == weeksBetween(endWeekCentralTime, currentDateCentralTime)
}


export default calcWeek;