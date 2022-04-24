
export enum SkillLevelEnum {
    BEGINNER = 'BEGINNER', 
    INTERMEDIATE = 'INTERMEDIATE',
    ADVANCED = 'ADVANCED', 
    EXPERT = 'EXPERT'
}

export const SkillLevelEnumDesc = new Map<string, string>([
    [SkillLevelEnum.BEGINNER, 'BEGINNER'],
    [SkillLevelEnum.INTERMEDIATE, 'INTERMEDIATE'],
    [SkillLevelEnum.ADVANCED, 'ADVANCED'],
    [SkillLevelEnum.EXPERT, 'EXPERT']
])