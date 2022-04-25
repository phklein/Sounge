
export enum WeekDayEnum {
    SUNDAY = 'SUNDAY',
    MONDAY = 'MONDAY',
    TUESDAY = 'TUESDAY',
    WEDNESDAY = 'WEDNESDAY',
    THURSDAY = 'THURSDAY',
    FRIDAY = 'FRIDAY', 
    SATURDAY = 'SATURDAY'
}

export const WeekDayEnumDesc = new Map<string, string>([
    [WeekDayEnum.SUNDAY, 'Domingo'],
    [WeekDayEnum.MONDAY, 'Segunda-feira'],
    [WeekDayEnum.TUESDAY, 'Terça-feira'],
    [WeekDayEnum.WEDNESDAY, 'Quarta-feira'],
    [WeekDayEnum.THURSDAY, 'Quinta-feira'],
    [WeekDayEnum.FRIDAY, 'Sexta-feira'],
    [WeekDayEnum.SATURDAY, 'Sábado']
])