import * as React from 'react';
import {Box, Button, ButtonGroup, Container, Stack} from "@mui/material";
import PropTypes from "prop-types";
import Typography from "@mui/material/Typography";
import {ArrowUpwardOutlined, EmailOutlined, Telegram} from "@mui/icons-material";

/**
 * Application footer
 */
export function AppFooter(props) {
    return (
        <Container maxWidth={"lg"} sx={{}}>
            <Box sx={{
                p: 4,
                backgroundColor: 'secondary.light',
                borderTopLeftRadius: 16,
                borderTopRightRadius: 16,
            }}>
                <Stack
                    direction="row"
                    justifyContent="space-between"
                    alignItems={'flex-start'}
                    spacing={2}
                >

                    <Container maxWidth={"sm"} sx={{margin: 0, p: '0 !important'}}>
                        <Stack
                            spacing={2}
                        >
                            <Typography variant="h5">
                                Shop
                                <Box component={'span'} sx={{
                                    color: 'secondary.main', ml: 1, fontWeight: '100'
                                }}>
                                    6 in 1
                                </Box>
                            </Typography>

                            <Typography variant="body2">
                                Store includes 6 applications written using Kotlin Multiplatform. All applications
                                use a common module and native UI for their platforms. All platforms use the latest
                                stack.
                            </Typography>

                            <Typography variant="caption">
                                © 2022 KeyGenQt
                            </Typography>
                        </Stack>
                    </Container>

                    <ButtonGroup
                        color="gray"
                        size="small"
                        variant="outlined"
                        aria-label="button group"
                    >
                        <Button onClick={() => {

                        }}>
                            <Telegram/>
                        </Button>
                        <Button onClick={() => {

                        }}>
                            <EmailOutlined/>
                        </Button>
                        <Button onClick={() => {

                        }}>
                            <ArrowUpwardOutlined/>
                        </Button>
                    </ButtonGroup>
                </Stack>
            </Box>
        </Container>
    );
}

AppFooter.propTypes = {
    disabled: PropTypes.bool,
};