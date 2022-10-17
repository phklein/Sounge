
export enum RoleNameEnum {
    GUITARIST = 'GUITARIST',
    EGUITARIST = 'EGUITARIST',
    VIOLINIST = 'VIOLINIST', 
    BASSPLAYER = 'BASSPLAYER', 
    UKULELEPLAYER = 'UKULELEPLAYER',
    DRUMMER = 'DRUMMER', 
    TAMBOURINEPLAYER = 'TAMBOURINEPLAYER',
    PIANIST = 'PIANIST', 
    EKEYBOARDPLAYER = 'EKEYBOARDPLAYER', 
    ACCORDIONIST = 'ACCORDIONIST',
    CORNETPLAYER = 'CORNETPLAYER', 
    TROMBONIST = 'TROMBONIST', 
    SAXOPHONIST = 'SAXOPHONIST', 
    FLUTIST = 'FLUTIST',
    DJ = 'DJ', 
    PRODUCER = 'PRODUCER', 
    VOCALIST = 'VOCALIST', 
    OTHERS = 'OTHERS'
}

export const RoleNameEnumDesc = new Map<string, string>([
    [RoleNameEnum.GUITARIST, 'Violinista'],
    [RoleNameEnum.EGUITARIST, 'Guitarrista'],
    [RoleNameEnum.VIOLINIST, 'Violonista'],
    [RoleNameEnum.BASSPLAYER, 'Baixista'],
    [RoleNameEnum.UKULELEPLAYER, 'Ukulelista'],
    [RoleNameEnum.DRUMMER, 'Trompetista'],
    [RoleNameEnum.TAMBOURINEPLAYER, 'Tambourineista'],
    [RoleNameEnum.PIANIST, 'Pianista'],
    [RoleNameEnum.EKEYBOARDPLAYER, 'Tecladista'],
    [RoleNameEnum.ACCORDIONIST, 'Sanfoneiro'],
    [RoleNameEnum.CORNETPLAYER, 'Cornetista'],
    [RoleNameEnum.TROMBONIST, 'Trombonista'],
    [RoleNameEnum.SAXOPHONIST, 'Saxofonista'],
    [RoleNameEnum.FLUTIST, 'Flautista'],
    [RoleNameEnum.DJ, 'DJ'],
    [RoleNameEnum.PRODUCER, 'Produtor'],
    [RoleNameEnum.VOCALIST, 'Vocalista'],
    [RoleNameEnum.OTHERS, 'Outros']
])