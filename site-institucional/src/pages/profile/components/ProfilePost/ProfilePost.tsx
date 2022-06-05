import React, { useEffect, useState } from "react"
import Swal from "sweetalert2"
import { Form } from "react-bootstrap"
import { withStyles } from "@mui/styles"
import { Autocomplete, Chip, InputAdornment, Stack, TextField, Button, CircularProgress } from "@mui/material"
import PostRoute from "../../../../routes/PostRoute"
import { GenreNameEnumDesc } from "../../../../enums/GenreNameEnum"
import "./ProfilePost.style.css"

const toBase64 = (file: any) =>
	new Promise((resolve, reject) => {
		const reader: any = new FileReader()
		reader.readAsDataURL(file)
		reader.onloadend = () => {
			resolve(reader.result && reader.result.replace("data:", "").replace(/^.+,/, ""))
		}
		reader.onerror = (error: any) => reject(error)
	})

const CustomTextField = withStyles({
	root: {
		"& .MuiInputBase-root": {
			color: "var(--white)",
		},
		"& label": {
			color: "var(--gray)",
		},
		"& label.Mui-focused": {
			color: "var(--white)",
		},
		"& .MuiInput-underline:after": {
			borderBottomColor: "var(--light-purple)",
		},
		"& .MuiOutlinedInput-root": {
			"& fieldset": {
				borderColor: "var(--gray)",
			},
			"&:hover fieldset": {
				borderColor: "var(--gray)",
			},
			"&.Mui-focused fieldset": {
				borderColor: "var(--light-purple)",
			},
		},
		"& .MuiAutocomplete-endAdornment > button": {
			color: "var(--white)",
		},
		backgroundColor: "var(--black-purple)",
		marginBottom: "8px",
		color: "var(--white)",
	},
})(TextField)

const CustomButton = withStyles({
	root: {
		"& > span": {
			color: "var(--light-purple)",
		},
	},
	disabled: {
		"&$disabled": {
			color: "var(--gray)",
		},
	},
})(Button)

const FORM_DEFAULT_VALUES = {
	text: "",
	mediaUrl: undefined,
	genres: [],
}

const ProfilePost = () => {
	const [unselectedGenres, setUnselectedGenres] = useState<any>(Array.from(GenreNameEnumDesc.keys()))
	const [loadingPostSubmit, setLoadingPostSubmit] = useState(false)
	const [disabledSubmit, setDisabledSubmit] = useState(true)
	const [formValues, setFormValues] = useState<{ text: string; mediaUrl: any; genres: any }>(FORM_DEFAULT_VALUES)

	useEffect(() => {
		console.log(formValues)
	}, [formValues])

	useEffect(() => {
		if (formValues.text) {
			setDisabledSubmit(false)
		} else {
			setDisabledSubmit(true)
		}
	}, [formValues])

	const submitPost = async (event: any) => {
		event.preventDefault()
		try {
			setLoadingPostSubmit(true)
			const viewerId = localStorage.getItem("viewerId") || null
			const response = await PostRoute.save({ ...formValues, userId: viewerId })
			if (response.status === 200) {
				Swal.fire("Post criado com sucesso!")
			}
		} catch (err) {
			console.log(err)
			Swal.fire("erro ao tentar realizar o post")
		} finally {
			setUnselectedGenres(Array.from(GenreNameEnumDesc.keys()))
			setFormValues(FORM_DEFAULT_VALUES)
			setLoadingPostSubmit(false)
		}
	}

	const handleGenreChange = (selectedGenre: any, selecting: boolean) => {
		if (selecting) {
			setUnselectedGenres(unselectedGenres.filter((genre: string) => genre !== selectedGenre))
			setFormValues({ ...formValues, genres: [...formValues.genres, selectedGenre] })
		} else {
			setUnselectedGenres([...unselectedGenres, selectedGenre])
			setFormValues({ ...formValues, genres: formValues.genres.filter((genre: string) => genre !== selectedGenre) })
		}
	}

	const handlePostImg = async (input: any) => {
		if (input.files && input.files.length) {
			const file = input.files[0]
			const formatedFile = await toBase64(file)
			setFormValues({ ...formValues, mediaUrl: formatedFile })
		}
	}

	return (
		<div className='profileShowcasePostWrapper'>
			<div className='profileShowcaseWrite'>
				<img
					className={
						formValues.mediaUrl ? "profileShowcaseWriteImage--visible" : "profileShowcaseWriteImage--invisible"
					}
					src={`data:image/png;base64,${formValues.mediaUrl}`}
				/>
				<Form onSubmit={submitPost}>
					<Form.Group className='profileShowcaseWriteInput' controlId='formGroupText'>
						<CustomTextField
							fullWidth
							multiline
							rows={6}
							variant='outlined'
							label='No que você está pensando?'
							value={formValues.text}
							onChange={(event: any) => setFormValues({ ...formValues, text: event.target.value })}
							InputProps={{
								endAdornment: (
									<InputAdornment position='end'>
										<label htmlFor='add-post-img'>+</label>
									</InputAdornment>
								),
							}}
						/>
						<input
							id='add-post-img'
							type='file'
							accept='image/png, image/jpeg'
							onChange={(event) => {
								handlePostImg(event.target)
							}}
						/>
					</Form.Group>
					<Form.Group controlId='formGroupGenres'>
						<Autocomplete
							autoSelect
							id='profileBandMemberFormInputMember'
							className=''
							noOptionsText='Nenhum gênero selecionável'
							options={unselectedGenres}
							getOptionLabel={(option: string) => GenreNameEnumDesc.get(option) || ""}
							isOptionEqualToValue={(option: string) => option === GenreNameEnumDesc.get(option)}
							onChange={(_, newSelectedGenre) => {
								newSelectedGenre && handleGenreChange(newSelectedGenre, true)
							}}
							renderInput={(params) => <CustomTextField {...params} label='Adicionar gêneros musicais' size='small' />}
						/>
						<Stack sx={{ padding: "4px", flexWrap: "wrap" }} direction='row'>
							{formValues.genres.map((genre: any, index: number) => (
								<Chip
									key={`${genre}-${index}`}
									sx={{
										backgroundColor: "var(--dark-purple)",
										color: "var(--white)",
										margin: "2px 4px 2px 4px",
										"&:hover": { backgroundColor: "var(--black-purple)" },
									}}
									label={GenreNameEnumDesc.get(genre)}
									onClick={() => handleGenreChange(genre, false)}
								/>
							))}
						</Stack>
					</Form.Group>

					<CustomButton
						disabled={disabledSubmit || loadingPostSubmit}
						variant='contained'
						color='success'
						type='submit'
					>
						{loadingPostSubmit ? <CircularProgress size={24} /> : "Publicar"}
					</CustomButton>
				</Form>
			</div>
			<div className='profileShowcasePosts'>
				<div className='profileShowcasePost'>
					<img
						className={"profileShowcasePostImage"}
					/>
          <span>AAAAAAAAAAAAAAAA</span>
          
				</div>
				<div className='profileShowcasePost'>a</div>
				<div className='profileShowcasePost'>a</div>
			</div>
		</div>
	)
}

export default ProfilePost
