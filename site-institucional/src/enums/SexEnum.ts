
export enum SexEnum {
    NULL = 'NULL', 
    MALE = 'MALE', 
    FEMALE = 'FEMALE', 
    NOT_APPLICABLE = 'NOT_APPLICABLE',
    NOT_KNOWN = 'NOT_KNOWN'
}

export const SexEnumDesc = new Map<string, string>([
    [SexEnum.MALE, 'Masculino'],
    [SexEnum.FEMALE, 'Feminino'],
    [SexEnum.NOT_APPLICABLE, 'Não aplicável'],
    [SexEnum.NOT_KNOWN, 'Não sei'],
])