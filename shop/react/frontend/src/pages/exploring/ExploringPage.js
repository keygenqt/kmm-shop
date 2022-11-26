import * as React from 'react';
import {
    Accordion,
    AccordionDetails,
    AccordionSummary,
    Box,
    Button,
    Card,
    CardActions,
    CardContent,
    CardMedia,
    Checkbox,
    Chip,
    FormControlLabel,
    FormGroup,
    Grid,
    IconButton,
    Pagination,
    Radio,
    RadioGroup,
    Slider,
    Stack,
    Typography,
    useMediaQuery,
    useTheme
} from "@mui/material";
import {useParams} from "react-router";
import {ConstantCollections} from "../../base/constants/ConstantCollections";
import {ConstantProducts} from "../../base/constants/ConstantProducts";
import {
    AddShoppingCartOutlined,
    CategoryOutlined,
    ExpandMoreOutlined,
    PriceChangeOutlined,
    SortOutlined,
    Star,
    StyleOutlined
} from "@mui/icons-material";
import {AppCache, ConstantStorage, useLocalStorage} from "../../base";
import {ValueType} from "../../base/route/ValueType";
import {SnackbarAddToCart} from "../../components/alerts/SnackbarAddToCart";

function valuetext(value) {
    return `${value}°C`;
}

export function ExploringPage() {

    let {filter} = useParams();

    const theme = useTheme()
    const isMD = useMediaQuery(theme.breakpoints.down('md'));
    const isSM = useMediaQuery(theme.breakpoints.down('sm'));

    const cartProducts = useLocalStorage(ConstantStorage.cart, ValueType.array, []);

    const [value, setValue] = React.useState([12, 87]);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const products = []

    ConstantProducts.slice(0, 6).forEach((product, index) => {
        const isCartAdded = Boolean(cartProducts.find((it) => it.id === product.id))
        products.push((
            <Grid key={`exploring-product-item-${index}`} item xl={4} lg={4} md={6} sm={12} xs={12} min={12} null={12}>
                <Box sx={{
                    p: 2,
                    borderRadius: 2,
                    backgroundColor: '#F6F7F9',
                }}>
                    <Card variant={'outlined'} sx={{
                        border: 'none',
                        position: 'relative',
                    }}>

                        <CardMedia
                            component="img"
                            height="200"
                            image={product.image}
                            alt="green iguana"
                        />

                        <CardContent>
                            <Stack spacing={1}>

                                <Stack>
                                    <Typography gutterBottom variant="h5" component="div">
                                        {product.title}
                                    </Typography>

                                    <Stack justifyContent={'center'} sx={{
                                        height: 40
                                    }}>
                                        <Typography className={'TextMax2'} variant="body2" color="text.secondary">
                                            {product.desc}
                                        </Typography>
                                    </Stack>

                                </Stack>

                                <Stack spacing={1} direction={'row'} justifyContent={'space-between'}>
                                    <Chip
                                        size={'medium'}
                                        label={product.price}
                                        variant={'outlined'}
                                        color={'success'}
                                        sx={{
                                            marginTop: '1px',
                                            minWidth: 100,
                                            borderWidth: 2,
                                            fontWeight: 400,
                                            fontSize: 14
                                        }}
                                    />

                                    <Stack direction={'row'} spacing={0.7} alignItems={'center'}>
                                        <Star sx={{width: 18, height: 18, color: '#FBBF23'}}/>
                                        <Typography variant="body2" color="text.secondary">
                                            4.0
                                        </Typography>
                                    </Stack>
                                </Stack>
                            </Stack>

                        </CardContent>

                        <CardActions disableSpacing>
                            <Stack spacing={1} sx={{width: '100%', mt: -1.5}}>
                                <Box sx={{
                                    height: 5,
                                    left: -10,
                                    width: 'calc(100% + 20px)',
                                    position: 'relative',
                                    backgroundColor: '#F6F7F9'
                                }}/>

                                <Stack direction={'row'} justifyContent={'space-between'} sx={{width: '100%'}}>
                                    <IconButton
                                        disabled={isCartAdded}
                                        size={'small'}
                                        onClick={() => {
                                            cartProducts.push(product)
                                            AppCache.arraySet(ConstantStorage.cart, cartProducts)
                                        }}
                                        sx={{
                                            borderColor: 'white',
                                            borderStyle: 'solid',
                                            borderWidth: 1,
                                            borderRadius: '50%',
                                            transitionProperty: 'border-color',
                                            transitionTimingFunction: 'ease-in-out',
                                            transitionDuration: '300ms',
                                            '&.Mui-disabled': {
                                                borderColor: 'primary.main',
                                            },
                                            '&.Mui-disabled .MuiSvgIcon-root': {
                                                color: 'primary.main'
                                            }
                                        }}
                                    >
                                        <Box sx={{
                                            p: 0.4,
                                            fontSize: 0,
                                        }}>
                                            <AddShoppingCartOutlined color={'gray'} sx={{
                                                transitionDuration: '300ms',
                                                fontSize: 18
                                            }}/>
                                        </Box>
                                    </IconButton>

                                    <Stack alignItems={'center'} justifyContent={'center'}>
                                        <Button variant={'outlined'} color={'secondary'} size={'small'}>
                                            View
                                        </Button>
                                    </Stack>

                                </Stack>
                            </Stack>

                        </CardActions>
                    </Card>
                </Box>
            </Grid>
        ))
    })

    return (
        <>
            <SnackbarAddToCart/>

            <Stack spacing={isSM ? 4 : 6}>

                <Stack spacing={2}>
                    <Typography variant={isSM ? 'h4' : 'h3'}>
                        {filter ? `${ConstantCollections.find((it) => it.key === filter)?.name ?? 'Best'} collection` : 'Exploring'}
                    </Typography>

                    <Typography variant={isSM ? 'h6' : 'h5'} sx={{
                        fontWeight: 100
                    }}>
                        Here you can choose your style for every day.
                    </Typography>
                </Stack>

                <Stack
                    direction={isSM ? 'column' : 'row'}
                    spacing={isSM ? 2 : 3}
                >
                    <Box sx={{
                        position: 'relative',
                        height: 'fit-content',
                    }}>
                        <Box
                            sx={{
                                height: 20,
                                width: 20,
                                bottom: -10,
                                left: 40,
                                borderRadius: '50%',
                                backgroundColor: 'success.main',
                                position: 'absolute',
                            }}
                        />

                        <Box
                            sx={{
                                height: 30,
                                width: 30,
                                top: -10,
                                right: 20,
                                borderRadius: '50%',
                                backgroundColor: '#FFD93A',
                                position: 'absolute',
                            }}
                        />

                        <Stack spacing={3} sx={{
                            p: isSM ? 2 : 3,
                            borderRadius: 2,
                            backgroundColor: '#F7F0EA',
                            minWidth: 230,
                        }}>
                            <Stack direction={'row'} spacing={1.4} alignItems={'center'}>
                                <CategoryOutlined sx={{width: 24, height: 24, color: '#d26900'}}/>
                                <Typography variant={'h5'}>
                                    Filters
                                </Typography>
                            </Stack>
                            <Box sx={{
                                '& .MuiPaper-root': {
                                    margin: '0px !important',
                                    boxShadow: 'none'
                                },
                                '& .MuiButtonBase-root': {
                                    minHeight: '48px !important',
                                },
                                '& .MuiAccordionSummary-content': {
                                    mt: '12px !important',
                                    mb: '12px !important'
                                }
                            }}>
                                <Accordion>
                                    <AccordionSummary
                                        expandIcon={<ExpandMoreOutlined/>}
                                        aria-controls="panel1a-content"
                                        id="panel1a-header"
                                    >
                                        <Stack direction={'row'} spacing={1.4} alignItems={'center'}>
                                            <CategoryOutlined sx={{width: 18, height: 18}}/>
                                            <Typography>Categories</Typography>
                                        </Stack>
                                    </AccordionSummary>
                                    <AccordionDetails>
                                        <FormGroup>
                                            <FormControlLabel control={<Checkbox defaultChecked/>} label="Bows"/>
                                            <FormControlLabel control={<Checkbox defaultChecked/>} label="Headbands"/>
                                            <FormControlLabel control={<Checkbox defaultChecked/>} label="Sets"/>
                                        </FormGroup>
                                    </AccordionDetails>
                                </Accordion>
                                <Accordion>
                                    <AccordionSummary
                                        expandIcon={<ExpandMoreOutlined/>}
                                        aria-controls="panel2a-content"
                                        id="panel2a-header"
                                    >
                                        <Stack direction={'row'} spacing={1.4} alignItems={'center'}>
                                            <StyleOutlined sx={{width: 18, height: 18}}/>
                                            <Typography>Collections</Typography>
                                        </Stack>
                                    </AccordionSummary>
                                    <AccordionDetails>
                                        <FormGroup>
                                            <FormControlLabel control={<Checkbox defaultChecked/>} label="School"/>
                                            <FormControlLabel control={<Checkbox defaultChecked/>} label="Walk"/>
                                            <FormControlLabel control={<Checkbox defaultChecked/>} label="Birthday"/>
                                            <FormControlLabel control={<Checkbox defaultChecked/>} label="Holiday"/>
                                            <FormControlLabel control={<Checkbox defaultChecked/>} label="New Year"/>
                                        </FormGroup>
                                    </AccordionDetails>
                                </Accordion>
                                <Accordion>
                                    <AccordionSummary
                                        expandIcon={<ExpandMoreOutlined/>}
                                        aria-controls="panel2a-content"
                                        id="panel2a-header"
                                    >
                                        <Stack direction={'row'} spacing={1.4} alignItems={'center'}>
                                            <SortOutlined sx={{width: 18, height: 18}}/>
                                            <Typography>Sort order</Typography>
                                        </Stack>
                                    </AccordionSummary>
                                    <AccordionDetails>
                                        <RadioGroup
                                            aria-labelledby="demo-radio-buttons-group-label"
                                            defaultValue="female"
                                            name="radio-buttons-group"
                                        >
                                            <FormControlLabel value="female" control={<Radio/>} label="Most Popular"/>
                                            <FormControlLabel value="male" control={<Radio/>} label="Best Rating"/>
                                            <FormControlLabel value="other" control={<Radio/>} label="Newest"/>
                                            <FormControlLabel value="other1" control={<Radio/>} label="Price Low"/>
                                            <FormControlLabel value="other2" control={<Radio/>} label="Price Hight"/>
                                        </RadioGroup>
                                    </AccordionDetails>
                                </Accordion>
                            </Box>
                            <Stack spacing={2}>
                                <Stack direction={'row'} spacing={1.4} alignItems={'center'} sx={{pt: 1}}>
                                    <PriceChangeOutlined sx={{width: 24, height: 24, color: '#d26900'}}/>
                                    <Typography variant={'h5'}>
                                        Price range
                                    </Typography>
                                </Stack>

                                <Box sx={{
                                    backgroundColor: 'white',
                                    borderRadius: 1,
                                    paddingY: 3,
                                    paddingX: 3
                                }}>
                                    <Typography variant={'caption'}>
                                        The sliders allow you to select from a range of prices.
                                    </Typography>

                                    <Box sx={{
                                        paddingX: 0.5,
                                        paddingTop: 1.2,
                                        paddingBottom: 1
                                    }}>
                                        <Slider
                                            color={'secondary'}
                                            getAriaLabel={() => 'Temperature range'}
                                            value={value}
                                            onChange={handleChange}
                                            valueLabelDisplay="auto"
                                            getAriaValueText={valuetext}
                                        />
                                    </Box>

                                    <Stack direction={'row'} justifyContent={'space-between'}>

                                        <Stack spacing={1} alignItems={'center'}>
                                            <Typography variant={'caption'} sx={{fontWeight: 600}}>
                                                Min price
                                            </Typography>

                                            <Chip
                                                size={'small'}
                                                label={value[0]}
                                                variant={'outlined'}
                                                color={'primary'}
                                                sx={{minWidth: 80, borderWidth: 2}}
                                            />
                                        </Stack>

                                        <Stack spacing={1} alignItems={'center'}>
                                            <Typography variant={'caption'} sx={{fontWeight: 600}}>
                                                Max price
                                            </Typography>

                                            <Chip
                                                size={'small'}
                                                label={value[1]}
                                                variant={'outlined'}
                                                color={'primary'}
                                                sx={{minWidth: 80, borderWidth: 2}}
                                            />
                                        </Stack>
                                    </Stack>

                                </Box>


                            </Stack>
                        </Stack>
                    </Box>

                    <Box sx={{position: 'relative'}}>

                        <Box
                            sx={{
                                height: 40,
                                width: 110,
                                top: -10,
                                right: 30,
                                borderRadius: 0.5,
                                backgroundColor: '#F09372',
                                position: 'absolute',
                            }}
                        />

                        <Stack spacing={isSM ? 2 : 3} sx={{position: 'relative'}}>
                            <Grid container spacing={isSM ? 2 : 3}>
                                {products}
                            </Grid>

                            <Pagination
                                count={10}
                                size={isSM ? 'small' : 'medium'}
                                variant="outlined"
                                color="secondary"
                            />
                        </Stack>
                    </Box>
                </Stack>
            </Stack>
        </>
    );
}

ExploringPage.propTypes = {};