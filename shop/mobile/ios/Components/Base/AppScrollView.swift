//
//  AppScrollView.swift
//  YouShop
//
//  Created by Виталий Зарубин on 22.12.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct AppScrollView<Content: View>: View {
    
    var padding: Edge.Set
    var content: () -> Content

    init(padding: Edge.Set = [.leading, .trailing, .top], @ViewBuilder content: @escaping () -> Content) {
        self.padding = padding
        self.content = content
    }
    
    var body: some View {
        ScrollView {
            if padding.isEmpty {
                VStack(spacing: 0) { content() }
            } else {
                VStack(spacing: 0) { content() }.paddingPage(padding)
            }
        }
    }
}
