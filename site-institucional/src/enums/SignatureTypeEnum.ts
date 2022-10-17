
export enum SignatureTypeEnum {
    MONTHLY = 'MONTHLY',
    SEMIANNUAL = 'SEMIANNUAL',
    YEARLY = 'YEARLY'
}

export const SignatureTypeEnumDesc = new Map<string, string>([
    [SignatureTypeEnum.MONTHLY, 'Mensal'],
    [SignatureTypeEnum.SEMIANNUAL, 'Semestral'],
    [SignatureTypeEnum.YEARLY, 'Anual']
])