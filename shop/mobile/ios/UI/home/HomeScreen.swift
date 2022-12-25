//
//  HomeScreen.swift
//  MyShop
//
//  Created by Виталий Зарубин on 19.12.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct HomeScreen: View {
    
    // Routing management
    @Environment(\.nav) var nav: NavChange
    
    // View Model
    @ObservedObject var viewModel = HomeViewModel()
    
    init() {
        print("-------------- HomeScreen")
        viewModel.load()
    }
    
    var body: some View {
        AppScrollView {
            AppSection(color: Color.bgVariant2) {
                InfoBlock()
            }
            if let response = viewModel.response {
                AppSection(color: Color.bgVariant1) {
                    CategoriesBlock(
                        categories: response,
                        actionAll: {
                            
                        },
                        actionItem: { title, id in
                            nav.add(NavScreens.products(
                                title: title,
                                categoryID: id
                            ))
                        }
                    )
                }
            } else {
                AppSection(color: Color.bgVariant1) {
                    VStack {
                        LoadingAnimationLarge()
                    }.paddingPage()
                }
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
        .background(Color.background)
    }
}
