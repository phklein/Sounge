
export enum GenreNameEnum {
    // Worldwide
    ROCK = 'ROCK', 
    METAL = 'METAL', 
    RAB = 'RAB', 
    POP = 'POP', 
    RAP = 'RAP', 
    TRAP = 'TRAP', 
    KPOP = 'KPOP',
    LOFI = 'LOFI', 
    INDIE = 'INDIE', 
    ELECTRONIC = 'ELECTRONIC', 
    CLASSICAL = 'CLASSICAL',

    // National Only
    FUNK = 'FUNK', 
    MPB = 'MPB', 
    SERTANEJO = 'SERTANEJO', 
    PAGODE = 'PAGODE', 
    FORRO = 'FORRO',

    // Special
    ECLECTIC = 'ECLECTIC'
}

export const GenreNameEnumDesc = new Map<string, string>([
    [GenreNameEnum.ROCK, 'Rock'],
    [GenreNameEnum.METAL, 'Metal'],
    [GenreNameEnum.RAB, 'R&B'],
    [GenreNameEnum.POP, 'Pop'],
    [GenreNameEnum.RAP, 'Rap'],
    [GenreNameEnum.TRAP, 'Trap'],
    [GenreNameEnum.KPOP, 'K-Pop'],
    [GenreNameEnum.LOFI, 'Lo-Fi'],
    [GenreNameEnum.INDIE, 'Indie'],
    [GenreNameEnum.ELECTRONIC, 'Electronic'],
    [GenreNameEnum.CLASSICAL, 'Classical'],
    [GenreNameEnum.FUNK, 'Funk'],
    [GenreNameEnum.MPB, 'MPB'],
    [GenreNameEnum.SERTANEJO, 'Sertanejo'],
    [GenreNameEnum.PAGODE, 'Pagode'],
    [GenreNameEnum.FORRO, 'Forró'],
    [GenreNameEnum.ECLECTIC, 'Ecléctico']
])