package tsykarev.grigorii.memorypuzzle.screens.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import tsykarev.grigorii.memorypuzzle.R
import tsykarev.grigorii.memorypuzzle.databinding.TitleFragmentBinding

class TitleFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val binding: TitleFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.title_fragment, container, false)

        binding.playGameButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_titleFragment_to_gameFragment)
        }
        return binding.root
    }
}