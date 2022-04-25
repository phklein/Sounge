
export enum SkillLevelEnum {
    NULL = 'NULL',
    BEGINNER = 'BEGINNER', 
    INTERMEDIATE = 'INTERMEDIATE',
    ADVANCED = 'ADVANCED', 
    EXPERT = 'EXPERT'
}

export const SkillLevelEnumDesc = new Map<string, string>([
    [SkillLevelEnum.BEGINNER, 'Iniciante'],
    [SkillLevelEnum.INTERMEDIATE, 'Intermediário'],
    [SkillLevelEnum.ADVANCED, 'Avançado'],
    [SkillLevelEnum.EXPERT, 'Especialista']
])